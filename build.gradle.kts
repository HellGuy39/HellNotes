buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Libs.Gradle.Plugin)
        classpath(Libs.Google.Hilt.Plugin)
        classpath(Libs.Kotlin.Plugin)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}