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

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}