enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
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
    }
}

rootProject.name = "PokemonApp"
include(":app")
include(":core:data")
include(":core:domain")
include(":core:test-fixtures")
include(":core:presentation:designsystem")
include(":core:presentation:ui")
include(":pokemon:data")
include(":pokemon:domain")
include(":pokemon:network")
include(":pokemon:presentation")
include(":core:database")
