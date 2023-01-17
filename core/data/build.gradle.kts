plugins {
    id("com.android.library")
    kotlin("android")
    kotlin ("kapt")
    id("com.google.dagger.hilt.android")
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.hellguy39.hellnotes.core.data"
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

    implementation(project(Project.Core.Database))
    implementation(project(Project.Core.Model))
    implementation(project(Project.Core.Domain))

    implementation(Libs.KotlinX.Coroutines)

    implementation(Libs.Google.Hilt.Android)
    kapt(Libs.Google.Hilt.Compiler)

}