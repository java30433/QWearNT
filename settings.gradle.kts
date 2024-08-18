pluginManagement {
    includeBuild("JustApkHook-plugin")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven("https://dl.google.com/dl/android/maven2/")
    }
}

rootProject.name = "QWearNT"
include(":app")
include(":JustApkHook-annotation")
include(":JustApkHook-annotation-compiler")
