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

/* Feature */
include (":feature:settings")
include (":feature:about-app")
include (":feature:note-detail")
include (":feature:note-list")
include (":feature:search")
include (":feature:reminders")