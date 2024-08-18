package bakuen.plugin.apkhook

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import kotlin.system.measureTimeMillis

class ApkHookPlugin : Plugin<Project> {
    companion object {
        lateinit var project: Project
            private set
    }

    override fun apply(project: Project) {
        ApkHookPlugin.project = project
        project.tasks.create("HookApk") { task ->
            task.dependsOn("assembleDebug")
            task.doLast {
                //log("Cleaning build cache...")
                //Config.srcApkDir.deleteRecursively()
                //Config.targetCopyDir.deleteRecursively()
                log("Cleaning source package cache...")
                Config.targetCopyDir.child("smali_classes7").deleteRecursively()
                log("Decompiling source apk...")
                Config.debugOutputDir.listFiles()?.find { it.name.lowercase().endsWith(".apk") }
                    ?.let {
                        brut.apktool.Main.main(
                            arrayOf(
                                "d",
                                it.absolutePath,
                                "-o",
                                Config.srcApkDir.absolutePath,
                                "-f"
                            )
                        )
                    } ?: throw FileNotFoundException("Cannot find release apk from source!")
                log("Copying target apk directory...")
                copyFolder(Config.targetDir, Config.targetCopyDir)
                val targetProject = ApkProject(Config.targetCopyDir)
                val sourceProject = ApkProject(Config.srcApkDir)
                log("Merging resources...")
                targetProject.mergeResourcesWith(sourceProject)
                log("Replacing Manifest...")
                Config.srcApkDir.child("AndroidManifest.xml")
                    .copyRecursively(Config.targetCopyDir.child("AndroidManifest.xml"), true)
                log("Hooking target apk...")
                targetProject.hookSmaliWith(sourceProject)
                log("Writing modifies to files...")
                targetProject.writeToDir(Config.targetCopyDir)
                log("Compiling hooked target apk...")
                brut.apktool.Main.main(
                    arrayOf(
                        "b",
                        Config.targetCopyDir.absolutePath,
                        "-o",
                        File(project.projectDir, "hook/hook.apk").absolutePath,
                        "-f"
                    )
                )
                log("hook.apk has been generous!")
            }
        }
    }
}

inline fun measureTimeAsSecond(block: () -> Unit): String {
    val timeInMillis = measureTimeMillis(block)
    val timeInSeconds = timeInMillis / 1000.0
    return "%.2f".format(timeInSeconds)
}

fun copyFolder(source: File, destination: File) {
    if (!source.exists() || !source.isDirectory) {
        throw IllegalArgumentException("Source folder does not exist or is not a directory")
    }

    if (!destination.exists()) {
        destination.mkdirs()
    }

    source.walkTopDown().forEach { sourceFile ->
        val destinationFile = File(destination, sourceFile.relativeTo(source).path)
        if (sourceFile.isDirectory) {
            if (!destinationFile.exists()) {
                destinationFile.mkdirs()
            }
        } else {
            if (destinationFile.exists()) {
                val sourceLastModified = sourceFile.lastModified()
                val destinationLastModified = destinationFile.lastModified()
                if (sourceLastModified <= destinationLastModified) {
                    return@forEach
                }
            }
            Files.copy(
                sourceFile.toPath(),
                destinationFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING
            )
        }
    }
}

fun <T> Iterable<T>.join(transform: ((T) -> CharSequence)? = null) =
    joinToString(separator = "", transform = transform)