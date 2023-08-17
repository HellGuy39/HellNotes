import install.installCoroutines
import install.installHilt

plugins {
    id(ProjectPlugin.Library)
}

android {
    namespace = "com.hellguy39.hellnotes.core.domain"
}

installCoroutines()
installHilt()

dependencies {

    implementation(project(Modules.Core.Common))
    implementation(project(Modules.Core.Model))

    implementation(Libs.AndroidX.AppCompat)
}