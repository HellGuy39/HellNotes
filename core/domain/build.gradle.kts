plugins {
    id("com.android.library")
    kotlin("android")
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.hellguy39.hellnotes.core.domain"
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(Project.Core.Model))

    implementation(Libs.AndroidX.AppCompat)

    implementation(Libs.Kotlin.Coroutines)

}