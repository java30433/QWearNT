package bakuen.plugin.apkhook.annotation

import com.google.auto.service.AutoService
import java.io.IOException
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.lang.model.SourceVersion
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@AutoService(Processor::class)
@SupportedAnnotationTypes("bakuen.plugin.apkhook.annotation.Copy")
class CopyProcessor : AbstractProcessor() {
    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        try {
            val methods =
                roundEnv.getElementsAnnotatedWith(Copy::class.java).map { it as ExecutableElement }
            if (methods.isEmpty()) return false
            val maps = mutableMapOf<TypeElement, MutableList<ExecutableElement>>()
            methods.forEach {
                maps.getOrPut(
                    it.enclosingElement as TypeElement,
                    defaultValue = { mutableListOf() }).add(it)
            }
            maps.forEach { (clazz, classMethods) ->
                write(clazz, classMethods)
            }
            return true
        } catch (e: IOException) {
            processingEnv.messager.printMessage(
                Diagnostic.Kind.ERROR,
                "Exception during annotation processing: ${e.message}"
            )
            return false
        }
    }

    private fun write(classElement: TypeElement, methods: List<ExecutableElement>) {
        val packageName =
            processingEnv.elementUtils.getPackageOf(classElement).qualifiedName.toString()
        val className = classElement.simpleName.toString()
        val source = StringBuilder()
        source.append("package $packageName;\n\n")
        source.append("public class ${className}COPY {\n")
        methods.forEach { method ->
            source.append("public static ${method.returnType} ${method.simpleName}${
                if (method.modifiers.contains(Modifier.PRIVATE)) "_private" else ""
            }${
                if (method.modifiers.contains(Modifier.STATIC)) "_static" else ""
            }(${
                method.parameters.joinToString(", ") { "${it.asType()} ${it.simpleName}" }
            }) { throw new RuntimeException(); }\n")
        }
        source.append("}\n")
        processingEnv.filer.createSourceFile(classElement.qualifiedName.toString() + "COPY")
            .openWriter().use { it.write(source.toString()) }
    }
}
