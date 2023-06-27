buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Libs.Gradle.Plugin)
        classpath(Libs.Google.Hilt.Plugin)
        classpath(Libs.Kotlin.Plugin)
        classpath(Libs.Kotlin.SerializationPlugin)
        classpath(Libs.Google.ServicesPlugin)
        classpath(Libs.Google.Firebase.CrashlyticsPlugin)
    }
}

plugins {
    id("com.google.devtools.ksp") version "1.8.21-1.0.11" apply false
    id("com.android.library") version "7.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.20" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}