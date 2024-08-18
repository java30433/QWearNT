package bakuen.plugin.apkhook

import java.io.File

class Smali(
    val sourceFile: File,
    val classpath: String
) {
    companion object {
        fun fromFile(file: File, rootDir: File) = Smali(
            sourceFile = file,
            classpath = file.absolutePath.removePrefix(rootDir.absolutePath).let {
                it.replace(it.first(), '.').drop(1).removeSuffix(".smali")
            }
        )
    }
    private var source: SmaliSource? = null
    fun hasSourceParsed() = source != null
    fun getSource() =
        if (source == null) SmaliSource.fromFile(sourceFile).also { source = it } else source!!

    fun getFile(rootDir: File) =
        File(rootDir, classpath.replace(".", File.separator).plus(".smali"))
}

class SmaliSource {
    val fields = mutableListOf<SmaliField>()
    val methods = mutableListOf<SmaliMethod>()
    var otherLines = ""
    fun toText() = "$otherLines\n${
        fields.join { field -> "\n.field ${field.modifiers.join { "$it " }}${field.name}:${field.type}${field.body}" }
    }\n${
        methods.join { method -> "\n${method.sign}${method.body}" }
    }"

    companion object {
        fun fromFile(source: File) = SmaliSource().apply {
            var text = source.readText()
            Regex(
                "^\\.field ((?:\\w+ )+)(\\w+):(\\S+)((?:.(?!^\\.(?!end)))*)",
                setOf(RegexOption.MULTILINE, RegexOption.DOT_MATCHES_ALL)
            ).findAll(text).forEach {
                fields.add(
                    SmaliField(
                        modifiers = it.groupValues[1].trim().split(" "),
                        name = it.groupValues[2],
                        type = it.groupValues[3],
                        body = it.groupValues[4]
                    )
                )
                text = text.replace(it.groupValues[0], "")
            }
            Regex(
                "\\.method ((?:\\w+ )*)([\\w$]+)\\((\\S*)\\)(\\S+)(.*?^\\.end method)",
                setOf(RegexOption.MULTILINE, RegexOption.DOT_MATCHES_ALL)
            ).findAll(text).forEach {
                methods.add(
                    SmaliMethod(
                        modifiers = it.groupValues[1].trim().split(" "),
                        name = it.groupValues[2],
                        args = it.groupValues[3],
                        returnType = it.groupValues[4],
                        body = it.groupValues[5]
                    )
                )
                text = text.replace(it.groupValues[0], "")
            }
            otherLines = text
        }
    }
}

class SmaliField(
    val modifiers: List<String>,
    val name: String,
    val type: String,
    val body: String
) {
    fun toCode() = ".field ${modifiers.join { "$it " }}$name:$type$body"
}

data class SmaliMethod(
    var modifiers: List<String>,
    val name: String,
    val args: String,
    val returnType: String,
    var body: String
) {
    val isPrivate get() = modifiers.contains("private")
    val sign get() = ".method ${modifiers.join { "$it " }}$name($args)$returnType"
    fun isEquals(other: SmaliMethod) = this.name == other.name && this.args == other.args && this.returnType == other.returnType
}