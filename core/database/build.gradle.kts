import install.installCoroutines
import install.installHilt
import install.installMoshi
import install.installRoom

plugins {
    id("com.google.devtools.ksp")
    id(ProjectPlugin.Library)
}

android {
    namespace = "com.hellguy39.hellnotes.core.database"

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