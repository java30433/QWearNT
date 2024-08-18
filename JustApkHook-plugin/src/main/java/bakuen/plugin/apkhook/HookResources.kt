package bakuen.plugin.apkhook

import org.gradle.api.Project
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.InputSource
import java.io.File
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

fun ApkProject.mergeResourcesWith(source: ApkProject) {
    val targetRes = this.rootDir.child("res")
    val sourceRes = source.rootDir.child("res")
    replaceResources.forEach { (s, t) ->
        sourceRes.child(s).copyRecursively(targetRes.child(t), true)
    }
}
val replaceResources = mutableMapOf<String, String>()

fun File.child(path: String) = File(this, path)
fun File.rename(name: String) = File(this.parentFile, name).also {
    it.deleteRecursively()
    this.renameTo(it)
}