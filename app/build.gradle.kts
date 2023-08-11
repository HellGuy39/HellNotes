import install.installAccompanist
import install.installAndroidCore
import install.installCompose
import install.installCoroutines
import install.installFirebase
import install.installHilt
import install.installTestingTools

plugins {
    id(ProjectPlugin.Application)
}

android {
    namespace = "com.hellguy39.hellnotes"

    defaultConfig {
        applicationId = "com.hellguy39.hellnotes"
    }

    applicationVariants.all {
        val isFirebaseEnabled =
            if (this.name == "release" || this.name == "beta") true.toString() else false.toString()

        buildConfigField(Boolean::class.java.typeName, "ENABLE_CRASHLYTICS", isFirebaseEnabled)
        buildConfigField(Boolean::class.java.typeName, "ENABLE_ANALYTICS", isFirebaseEnabled)
    }

    buildTypes {
        create("benchmark") {
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
            isShrinkResources = false
            isMinifyEnabled = false
        }
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
            excludes.add("/META-INF/INDEX.LIST")
        }
    }
}

installFirebase()
installAndroidCore()
installHilt()
installCompose()
installAccompanist()
installCoroutines()
installTestingTools()

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

    implementation(Libs.Google.Material)
    implementation(Libs.AndroidX.Biometric)
    implementation(Libs.AndroidX.SplashScreen)
    implementation(Libs.AndroidX.ProfileInstaller)
}