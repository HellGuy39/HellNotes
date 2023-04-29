plugins {
    id("com.android.library")
    kotlin("android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    kotlin ("kapt")
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.hellguy39.hellnotes.core.domain"
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    implementation(project(Modules.Core.Model))

    implementation(Libs.AndroidX.AppCompat)

    implementation(Libs.Kotlin.Coroutines)

    implementation(Libs.Google.Hilt.Android)
    kapt(Libs.Google.Hilt.Compiler)

    testImplementation(Libs.JUnit)
    androidTestImplementation(Libs.AndroidX.JUnit)
    androidTestImplementation(Libs.AndroidX.Espresso)

}