
pluginManagement {
    repositories {
        google()
        mavenCentral()
    }
    plugins {
        id("org.jetbrains.kotlin.plugin.compose") version "2.1.10"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "BasicLearning"
include(":app")
 