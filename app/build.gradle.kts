plugins {
    id("com.android.application")
    kotlin("android")
    kotlin ("kapt")
    id("com.google.dagger.hilt.android")
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.hellguy39.hellnotes"
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.ApplicationId
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = 1
        versionName = "1.0-beta01"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(project(Project.Feature.Reminders))
    implementation(project(Project.Feature.Search))
    implementation(project(Project.Feature.NoteList))
    implementation(project(Project.Feature.NoteDetail))
    implementation(project(Project.Feature.AboutApp))
    implementation(project(Project.Feature.Settings))

    implementation(project(Project.Core.Ui))
    implementation(project(Project.Core.Data))
    implementation(project(Project.Core.Common))
    implementation(project(Project.Core.Domain))
    implementation(project(Project.Core.Database))
    implementation(project(Project.Core.Model))

    implementation(Libs.AndroidX.CoreKtx)
    implementation(Libs.AndroidX.LifecycleKtx)
    implementation(Libs.AndroidX.AppCompat)
    implementation(Libs.AndroidX.Biometric)
    implementation(Libs.AndroidX.SplashScreen)

    implementation(Libs.Google.Material)

    implementation(Libs.AndroidX.Compose.Lifecycle)
    implementation(Libs.AndroidX.Compose.Activty)
    implementation(Libs.AndroidX.Compose.Ui)
    implementation(Libs.AndroidX.Compose.ToolingPreview)
    implementation(Libs.AndroidX.Compose.Material3)
    implementation(Libs.AndroidX.Compose.Navigation)
    androidTestImplementation(Libs.AndroidX.Compose.UiTestJUnit)
    debugImplementation(Libs.AndroidX.Compose.UiTooling)
    debugImplementation(Libs.AndroidX.Compose.UiTestManifest)

    testImplementation(Libs.JUnit)
    androidTestImplementation(Libs.AndroidX.JUnit)
    androidTestImplementation(Libs.AndroidX.Espresso)

    implementation(Libs.Google.Accompanist.NavigationAnimation)
    implementation(Libs.Google.Accompanist.SystemUiController)

    implementation(Libs.AndroidX.Room.RoomKtx)
    kapt(Libs.AndroidX.Room.RoomCompiler)

    implementation(Libs.KotlinX.Coroutines)

    implementation(Libs.Google.Hilt.Android)
    kapt(Libs.Google.Hilt.Compiler)
    implementation(Libs.Google.Hilt.NavigationCompsoe)

    implementation(Libs.SquareUp.Moshi)

}