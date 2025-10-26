pluginManagement {
    plugins {
        kotlin("jvm") version "1.9.21"
    }
}

rootProject.name = "lava-deezer-mirror"


dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
