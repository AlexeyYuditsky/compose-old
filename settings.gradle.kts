pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "composeold"
include(":instagram")
include(":vkclient")
include(":composeTest")
include(":activityResultApiOld")
include(":activityResultApiNew")
