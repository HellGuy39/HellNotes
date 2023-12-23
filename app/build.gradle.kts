import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {
    id("app-setup")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.hellguy39.hellnotes"

    defaultConfig {
        archivesName.set("HellNotes v$versionName")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
        create("benchmark") {
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
        }
    }
}

dependencies {

    implementation(project(Modules.Feature.Startup))
    implementation(project(Modules.Feature.OnBoarding))
    implementation(project(Modules.Feature.Lock))
    implementation(project(Modules.Feature.Search))
    implementation(project(Modules.Feature.Home))
    implementation(project(Modules.Feature.LabelEdit))
    implementation(project(Modules.Feature.NoteDetail))
    implementation(project(Modules.Feature.ReminderEdit))
    implementation(project(Modules.Feature.AboutApp))
    implementation(project(Modules.Feature.Settings))
    implementation(project(Modules.Feature.LockSelection))
    implementation(project(Modules.Feature.LanguageSelection))
    implementation(project(Modules.Feature.LockSetup))
    implementation(project(Modules.Feature.LabelSelection))
    implementation(project(Modules.Feature.NoteStyleEdit))
    implementation(project(Modules.Feature.NoteSwipesEdit))
    implementation(project(Modules.Feature.Changelog))
    implementation(project(Modules.Feature.PrivacyPolicy))
    implementation(project(Modules.Feature.TermsAndConditions))
    implementation(project(Modules.Feature.Reset))
    implementation(project(Modules.Feature.Update))
    implementation(project(Modules.Feature.Backup))

    implementation(project(Modules.Core.Ui))
    implementation(project(Modules.Core.Data))
    implementation(project(Modules.Core.Common))
    implementation(project(Modules.Core.Domain))
    implementation(project(Modules.Core.Database))
    implementation(project(Modules.Core.Datastore))
    implementation(project(Modules.Core.Model))

    implementation(Dependencies.AndroidX.CoreKtx)
    implementation(Dependencies.AndroidX.LifecycleKtx)
    implementation(Dependencies.AndroidX.AppCompat)
    implementation(Dependencies.AndroidX.WorkKtx)
    implementation(Dependencies.AndroidX.Biometric)
    implementation(Dependencies.AndroidX.SplashScreen)
    implementation(Dependencies.AndroidX.ProfileInstaller)

    implementation(Dependencies.Google.Material)

    implementation(Dependencies.Compose.Lifecycle)
    implementation(Dependencies.Compose.Activity)
    implementation(Dependencies.Compose.Ui)
    implementation(Dependencies.Compose.ToolingPreview)
    implementation(Dependencies.Compose.Material3)
    implementation(Dependencies.Compose.Navigation)
    androidTestImplementation(Dependencies.Compose.UiTestJUnit)
    debugImplementation(Dependencies.Compose.UiTooling)
    debugImplementation(Dependencies.Compose.UiTestManifest)

    testImplementation(Dependencies.JUnit)
    androidTestImplementation(Dependencies.AndroidX.JUnit)
    androidTestImplementation(Dependencies.AndroidX.Espresso)

    implementation(Dependencies.Room.RoomKtx)
    ksp(Dependencies.Room.RoomCompiler)

    implementation(Dependencies.Kotlin.Coroutines)

    implementation(Dependencies.Hilt.Android)
    ksp(Dependencies.Hilt.Compiler)
    implementation(Dependencies.Hilt.NavigationCompose)

    implementation(Dependencies.SquareUp.Moshi)

    implementation(Dependencies.Firebase.Analytics)
    implementation(Dependencies.Firebase.Crashlytics)

    implementation(Dependencies.RuStore.AppUpdate)
}
