//buildscript {
//    repositories {
//        google()
//        mavenCentral()
//    }
////    dependencies {
////        //classpath(Libs.Gradle.Plugin)
////        //classpath(Libs.Google.Hilt.Plugin)
////        //classpath(Libs.Kotlin.Plugin)
////        //classpath(Libs.Kotlin.SerializationPlugin)
////        //classpath(Libs.Google.ServicesPlugin)
////        //classpath(Libs.Google.Firebase.CrashlyticsPlugin)
////    }
//}

plugins {
    //id("com.android.application") version "8.0.2" apply false
    //id("com.android.library") version "8.0.2" apply false
    //id("org.jetbrains.kotlin.android") version "1.8.21" apply false

    id("com.google.dagger.hilt.android") version "2.46" apply false
    id("com.google.devtools.ksp") version "1.8.21-1.0.11" apply false
    id("com.google.gms.google-services") version "4.3.15" apply false
    id("com.google.firebase.crashlytics") version "2.9.7" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.21" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}