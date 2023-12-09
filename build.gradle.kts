buildscript {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
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
    id("com.google.devtools.ksp") version "1.9.20-1.0.14" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}