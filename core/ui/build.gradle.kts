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
    namespace = "com.hellguy39.hellnotes.core.ui"

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}

installHilt()
installCoroutines()
installHilt()
installCompose()
installAccompanist()

dependencies {

    implementation(project(Modules.Core.Common))
    implementation(project(Modules.Core.Model))

    implementation(Libs.AndroidX.CoreKtx)
    implementation(Libs.AndroidX.LifecycleKtx)
    implementation(Libs.AndroidX.AppCompat)

    androidTestImplementation(Libs.Testing.UiTestJUnit)
    debugImplementation(Libs.Testing.UiTooling)
    debugImplementation(Libs.Testing.UiTestManifest)

}