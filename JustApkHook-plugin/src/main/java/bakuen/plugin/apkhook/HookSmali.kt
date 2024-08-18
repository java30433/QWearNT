package bakuen.plugin.apkhook

import java.io.File

val ignoreClassKeys = listOf(
    "androidx"
)
val injectSmaliList = mutableListOf<InjectSmali>()
fun ApkProject.hookSmaliWith(source: ApkProject) {
    injectSmaliList.forEach { inject ->
        //TODO 优化
        this.findSmali(Smali.fromFile(File(this.rootDir, inject.path), this.rootDir))?.let { smail ->
            inject.injects.forEach {
                when (it) {
                    is InjectType.ReplaceStr -> smail.sourceFile.writeText(smail.sourceFile.readText().replace(it.ori, it.new))
                    is InjectType.ReplaceLine -> smail.sourceFile.writeText(smail.sourceFile.readLines().toMutableList().apply { set(it.line-1, it.value) }.joinToString("\n"))
                }
            }
        }
    }
    source.foreachSmali { sourceDex, sourceSmali ->
        if (sourceSmali.classpath.endsWith("COPY") || sourceSmali.classpath.contains("androidx")) {
            sourceDex.smaliClasses.remove(sourceSmali)
            return@foreachSmali
        }
        val targetSmali = this.findSmali(sourceSmali) ?: return@foreachSmali
        log("Hooking ${targetSmali.classpath} ...")
        sourceDex.smaliClasses.remove(sourceSmali)
        val targetSource = targetSmali.getSource()
        val sourceSource = sourceSmali.getSource()
        targetSource.otherLines = targetSource.otherLines.replace(".class public final ", ".class public ")
        sourceSource.methods.toMutableList().forEach { sourceMethod ->
            val targetMethod = targetSource.methods.find { it.isEquals(sourceMethod) }
            if (targetMethod == null) {
                targetSource.methods.add(sourceMethod)
                return@forEach
            }
            log("Find target method ${targetMethod.sign}")
            //处理COPY类调用
            sourceMethod.body = sourceMethod.body.replace("invoke-static/range {p1 .. p2}", "invoke-static {p1, p2}")
            sourceMethod.body = sourceMethod.body.replace("invoke-static/range {p1 .. p3}", "invoke-static {p1, p2, p3}")
            sourceMethod.body = sourceMethod.body.replace("invoke-static/range {p1 .. p4}", "invoke-static {p1, p2, p3, p4}")
            sourceMethod.body = sourceMethod.body.replace("invoke-static/range {p1 .. p5}", "invoke-static {p1, p2, p3, p4, p5}")
            sourceMethod.body = sourceMethod.body.replace("invoke-static/range {p1 .. p6}", "invoke-static {p1, p2, p3, p4, p5, p6}")
            sourceMethod.body = sourceMethod.body.replace(
                "invoke-static \\{(.*)\\}, (.*)COPY;->(.*?)(_private)?(_static)?\\((.*)".toRegex()
            ) {
                "invoke-${
                    if (it.groupValues[5].isEmpty())
                        if (it.groupValues[4].isEmpty()) "virtual {p0, " else "direct {p0, "
                    else "static {"
                }${
                    if (it.groupValues[1].isEmpty()) "" else it.groupValues[1]
                }}, ${it.groupValues[2]};->${it.groupValues[3]}COPY(${it.groupValues[6]}"
            }
            sourceMethod.body = sourceMethod.body.replace("invoke-virtual {p0, p1, p2}", "invoke-virtual/range {p0 .. p2}")
            sourceMethod.body = sourceMethod.body.replace("invoke-virtual {p0, p1, p2, p3}", "invoke-virtual/range {p0 .. p3}")
            sourceMethod.body = sourceMethod.body.replace("invoke-virtual {p0, p1, p2, p3, p4}", "invoke-virtual/range {p0 .. p4}")
            sourceMethod.body = sourceMethod.body.replace("invoke-virtual {p0, p1, p2, p3, p4, p5}", "invoke-virtual/range {p0 .. p5}")
            sourceMethod.body = sourceMethod.body.replace("invoke-virtual {p0, p1, p2, p3, p4, p5, p6}", "invoke-virtual/range {p0 .. p6}")
            sourceMethod.body = sourceMethod.body.replace("invoke-virtual {p0, },", "invoke-virtual {p0},")
            //TODO 注解处理抽象化
            if (sourceMethod.body.contains(".annotation runtime Lbakuen/plugin/apkhook/annotation/Copy;")) {
                targetSource.methods.add(targetMethod.copy(name = "${targetMethod.name}COPY"))
            }
            if (sourceMethod.body.contains(".annotation runtime Lbakuen/plugin/apkhook/annotation/Replace;")) {
                targetMethod.body = sourceMethod.body
            }
        }
        targetSource.fields.addAll(sourceSource.fields)
    }
    this.dexList.addAll(source.dexList)
}