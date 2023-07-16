import install.installCoroutines
import install.installHilt
import install.installMoshi
import install.installRoom

plugins {
    id("com.android.library")
    kotlin("android")
    id("com.google.devtools.ksp")
    id(ProjectPlugin.DefaultConfig)
    id(ProjectPlugin.JavaCompile)
}

android {
    namespace = "com.hellguy39.hellnotes.core.database"

    buildTypes {
        release {
            isMinifyEnabled = true
        }
    }
    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}

installMoshi()
installCoroutines()
installHilt()
installRoom()

dependencies {

    implementation(project(Modules.Core.Model))
}