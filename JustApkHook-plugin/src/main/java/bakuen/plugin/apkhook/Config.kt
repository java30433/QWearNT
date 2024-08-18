package bakuen.plugin.apkhook

import bakuen.plugin.apkhook.ApkHookPlugin.Companion.project
import bakuen.plugin.apkhook.Config.LOG_PREFIX
import org.gradle.api.Project
import java.io.File

inline fun Project.apkHook(block: Config.()->Unit) {
    block.invoke(Config)
}

fun log(msg: String) = println("$LOG_PREFIX $msg")
fun err(msg: String) = System.err.println("$LOG_PREFIX $msg")

object Config {
    const val LOG_PREFIX = "[ApkHookPlugin]"
    val targetDir = File(project.projectDir, "src/main/apkhook")
    val srcApkDir = File(project.projectDir, "build/srcApkCache")
    val targetCopyDir = File(project.projectDir, "build/targetCopy")
    val debugOutputDir = File(project.projectDir, "build/outputs/apk/debug")
    fun resources(block: ResourcesConfig.()->Unit) {
        ResourcesConfig.block()
    }
    fun smali(path: String, block: SmaliConfig.()->Unit) {
        SmaliConfig(path).block()
    }
}

object ResourcesConfig {
    fun replace(type: String, suffix: String, source: String, target: String) {
        replaceResources["$type/$source.$suffix"] = "$type/$target.$suffix"
    }
    fun replace(type: String, name: String) {
        replaceResources["$type/$name"] = "$type/$name"
    }
    fun replace(fromType: String, toType: String, name: String) {
        replaceResources["$fromType/$name"] = "$toType/$name"
    }
}

class SmaliConfig(path: String) {
    private val injectSmali = InjectSmali(path)
    init {
        injectSmaliList.add(injectSmali)
    }
    fun replaceStr(ori: String, new: String) {
        injectSmali.injects.add(InjectType.ReplaceStr(ori, new))
    }
    fun replaceLine(line: Int, value: String) {
        injectSmali.injects.add(InjectType.ReplaceLine(line, value))
    }
}