plugins {
    id("com.android.library")
    kotlin("android")
    kotlin ("kapt")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.hellguy39.hellnotes.core.database"
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
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

    api(Libs.AndroidX.Room.RoomKtx)
    ksp(Libs.AndroidX.Room.RoomCompiler)

    implementation (Libs.Kotlin.Coroutines)

    implementation(Libs.Google.Hilt.Android)
    kapt(Libs.Google.Hilt.Compiler)

    implementation(Libs.SquareUp.Moshi)
    implementation(Libs.SquareUp.MoshiKotlin)
}