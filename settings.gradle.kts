enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.google")
                includeGroupAndSubgroups("com.android")
            }
        }
        gradlePluginPortal()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

rootProject.name = "iosApp"

include(":androidApp")
include(":feature")
include(":feature:home")
include(":core")
include(":core:model")
include(":core:movies-api")
include(":core:database")
include(":core:uikit")
include(":core:data")
include(":core:domain")
include(":feature:details")
include(":feature:search")
include(":feature:favorites")
include(":core:utils")
include(":feature:actor")
include(":feature:movie_category")
include(":core:datastore")
include(":shared")
include(":composeApp")
