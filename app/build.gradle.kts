import install.installAccompanist
import install.installCompose
import install.installCoroutines
import install.installFirebase
import install.installHilt

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("java-compile-plugin")
    id("default-config-plugin")
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.hellguy39.hellnotes"

    defaultConfig {
        applicationId = Config.applicationId
    }

    applicationVariants.all {
        val isFirebaseEnabled =
            if (this.name == "release" || this.name == "beta") true.toString() else false.toString()

        buildConfigField(Boolean::class.java.typeName, "ENABLE_CRASHLYTICS", isFirebaseEnabled)
        buildConfigField(Boolean::class.java.typeName, "ENABLE_ANALYTICS", isFirebaseEnabled)
    }

    flavorDimensions.add("type")

    productFlavors {
        create("development") {
            dimension = "type"
        }
        create("production") {
            dimension = "type"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        create("benchmark") {
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
            isMinifyEnabled = false
        }

        create("beta") {
            isMinifyEnabled = true
        }

        debug {
            isMinifyEnabled = false
            isDebuggable = true
        }
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
            excludes.add("/META-INF/INDEX.LIST")
        }
    }
    kapt {
        correctErrorTypes = true
    }
}

installFirebase()
installHilt()
installCompose()
installAccompanist()
installCoroutines()

dependencies {

    implementation(project(Modules.Feature.Startup))
    implementation(project(Modules.Feature.OnBoarding))
    implementation(project(Modules.Feature.Lock))
    implementation(project(Modules.Feature.Search))
    implementation(project(Modules.Feature.Home))
    implementation(project(Modules.Feature.LabelEdit))
    //implementation(project(Modules.Feature.NoteDetail))
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

    implementation(Libs.AndroidX.CoreKtx)
    implementation(Libs.AndroidX.LifecycleKtx)
    implementation(Libs.AndroidX.AppCompat)
    implementation(Libs.AndroidX.WorkKtx)
    implementation(Libs.AndroidX.Biometric)
    implementation(Libs.AndroidX.SplashScreen)
    implementation(Libs.AndroidX.ProfileInstaller)

    androidTestImplementation(Libs.Testing.Work)
    androidTestImplementation(Libs.Testing.UiTestJUnit)
    debugImplementation(Libs.Testing.UiTooling)
    debugImplementation(Libs.Testing.UiTestManifest)
    testImplementation(Libs.Testing.JUnit)
    androidTestImplementation(Libs.Testing.AndroidJUnit)
    androidTestImplementation(Libs.Testing.Espresso)
}