pluginManagement {
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

rootProject.name = "PosNativeApp"
include(":app")
include(":client")
include(":core")
include(":navigation")
include(":feat:auth")
include(":previewData")
include(":components")
include(":test:clientTest")
include(":test:uiTest")
include(":data")
include(":domain")
include(":offline")
include(":manager")
include(":feat:onboarding")
include(":feat:order")
