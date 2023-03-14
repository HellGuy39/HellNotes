plugins {
    id("com.android.library")
    kotlin("android")
    kotlin ("kapt")
    id("com.google.dagger.hilt.android")
}
android {
    namespace = "com.hellguy39.hellnotes.core.datastore"
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

    implementation (Libs.Kotlin.Coroutines)

    implementation(Libs.Google.Hilt.Android)
    kapt(Libs.Google.Hilt.Compiler)

    implementation(project(Modules.Core.Model))

    implementation (Libs.AndroidX.DataStore.Preferences)
}