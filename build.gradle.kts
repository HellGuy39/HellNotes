buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://artifactory-external.vkpartner.ru/artifactory/maven")
    }
    dependencies {
        classpath(Dependencies.Android.GradlePlugin)
        classpath(Dependencies.Hilt.GradlePlugin)
        classpath(Dependencies.Kotlin.GradlePlugin)
        classpath(Dependencies.Kotlin.GradleSerializationPlugin)
        classpath(Dependencies.Google.GradleServicesPlugin)
        classpath(Dependencies.Firebase.CrashlyticsPlugin)
    }
}

plugins {
    id("com.google.devtools.ksp") version "1.9.22-1.0.17" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}