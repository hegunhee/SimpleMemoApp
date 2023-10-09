pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "NewSimpleMemoApp"
include (":app")
include(":domain")
include(":data")
include(":feature")
include(":compose-app")
include(":compose-feature")
include(":common_resource")
