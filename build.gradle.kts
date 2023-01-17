buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.0")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.44.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")
    }
}

//plugins {
//    id 'com.android.application' version '7.4.0' apply false
//    id 'com.android.library' version '7.4.0' apply false
//    id 'org.jetbrains.kotlin.android' version "$kotlin_version" apply false
//    id 'com.google.dagger.hilt.android' version "$hilt_version" apply false
//}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}