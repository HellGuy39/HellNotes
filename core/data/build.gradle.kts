import install.installCoroutines
import install.installHilt

plugins {
    id("com.android.library")
    kotlin("android")
    id(ProjectPlugin.JavaCompile)
    id(ProjectPlugin.DefaultConfig)
}

android {
    namespace = "com.hellguy39.hellnotes.core.data"

    buildTypes {
        release {
            isMinifyEnabled = true
        }
    }
}

installHilt()
installCoroutines()

dependencies {

    implementation(project(Modules.Core.Database))
    implementation(project(Modules.Core.Datastore))
    implementation(project(Modules.Core.Network))
    implementation(project(Modules.Core.Model))
    implementation(project(Modules.Core.Domain))

}