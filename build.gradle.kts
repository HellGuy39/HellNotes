buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.0")
        classpath(Libs.Google.Hilt.Plugin)
        classpath(Libs.Kotlin.Plugin)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}