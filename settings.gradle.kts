pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "HellNotes"

/* App */
include (":app")

/* Core */
include (":core:database")
include (":core:data")
include (":core:model")
include (":core:domain")
include (":core:ui")
include (":core:common")
include (":core:datastore")

/* Feature */
include (":feature:settings")
include (":feature:about-app")
include (":feature:note-detail")
include (":feature:home")
include (":feature:search")
include (":feature:labels")
include (":feature:welcome")
include(":feature:lock")
include(":feature:lock-selection")
include(":feature:language-selection")
include(":feature:lock-setup")
