plugins {
    `java-gradle-plugin`
    id("org.jetbrains.kotlin.jvm") version "1.9.0" apply true
}

gradlePlugin {
    plugins {
        create("JustApkHook") {
            id = "bakuen.plugin.apkhook"
            implementationClass = "bakuen.plugin.apkhook.ApkHookPlugin"
        }
    }
}

repositories {
    mavenCentral()
    jcenter()
    google()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    compileOnly(gradleApi())
    implementation(kotlin("stdlib"))
    implementation(fileTree("./src/main/libs") {
        include("*.jar")
    })
}

