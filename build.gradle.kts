buildscript {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
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
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}