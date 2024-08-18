package bakuen.plugin.apkhook

import java.io.File

class Dex(
    val smaliClasses: MutableList<Smali> = mutableListOf()
) {
    companion object {
        fun fromDir(dir: File) = Dex(
            smaliClasses = dir.walk().filter { it.isFile && it.name.endsWith(".smali") }.toList()
                .map { file -> Smali.fromFile(file, dir) }.toMutableList()
        )
    }
}