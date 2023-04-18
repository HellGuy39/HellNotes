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
        maven { setUrl("https://jitpack.io") }
    }
}

rootProject.name = "HellNotes"

/* App */
include (":app")
include(":benchmark")

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
include (":feature:label-edit")
include (":feature:on-boarding")
include(":feature:lock")
include(":feature:lock-selection")
include(":feature:language-selection")
include(":feature:lock-setup")
include(":feature:reminder-edit")
include(":feature:label-selection")
include(":feature:note-style-edit")
include(":feature:note-swipe-edit")
include(":feature:startup")
include(":feature:changelog")
include(":feature:reset")
include(":feature:privacy-policy")
include(":feature:terms-and-conditions")
include(":core:network")
