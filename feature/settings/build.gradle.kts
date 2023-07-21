import install.installAccompanist
import install.installCompose
import install.installCoroutines
import install.installHilt

plugins {
    id("com.android.library")
    kotlin("android")
    id(ProjectPlugin.JavaCompile)
    id(ProjectPlugin.DefaultConfig)
}

android {
    namespace = "com.hellguy39.hellnotes.feature.settings"

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}

installHilt()
installCoroutines()
installCompose()
installAccompanist()

dependencies {

    implementation(project(Modules.Core.Ui))
    implementation(project(Modules.Core.Domain))
    implementation(project(Modules.Core.Model))
    implementation(project(Modules.Core.Common))

    implementation(Libs.AndroidX.CoreKtx)
    implementation(Libs.AndroidX.LifecycleKtx)
    implementation(Libs.AndroidX.AppCompat)

    androidTestImplementation(Libs.Testing.UiTestJUnit)
    debugImplementation(Libs.Testing.UiTooling)
    debugImplementation(Libs.Testing.UiTestManifest)

}