package bakuen.plugin.apkhook

import java.io.File

class ApkProject(val rootDir: File) {
    val dexList: MutableList<Dex> = rootDir.listFiles { _, name -> name.startsWith("smali") }.orEmpty()
        .map { Dex.fromDir(it) }.toMutableList()

    inline fun foreachSmali(block: (Dex, Smali) -> Unit) {
        dexList.toMutableList().forEach { dexClasses ->
            dexClasses.smaliClasses.toMutableList().forEach {
                block.invoke(dexClasses, it)
            }
        }
    }

    fun findSmali(smali: Smali): Smali? {
        foreachSmali { _, smaliFile ->
            if (smaliFile.classpath == smali.classpath) {
                return smaliFile
            }
        }
        return null
    }

    fun writeToDir(dir: File) {
        dexList.forEachIndexed { index, dex ->
            val root = File(dir, "smali${if (index > 0) "_classes${index+1}" else ""}")
            dex.smaliClasses.forEach {
                val file = it.getFile(root)
                if (!file.exists()) {
                    file.parentFile.mkdirs()
                    it.sourceFile.copyTo(file, true)
                }
                if (!it.hasSourceParsed()) return@forEach
                file.writeText(it.getSource().toText())
            }
        }
    }
}