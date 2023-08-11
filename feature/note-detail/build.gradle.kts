import install.installAccompanist
import install.installAndroidCore
import install.installBaseProjectCore
import install.installCompose
import install.installCoroutines
import install.installHilt

plugins {
    id(ProjectPlugin.Feature)
}

android {
    namespace = "com.hellguy39.hellnotes.feature.note_detail"
}

installBaseProjectCore()
installAndroidCore()
installCoroutines()
installAccompanist()
installCompose()
installHilt()

dependencies {
    implementation(Libs.ThirdParty.ComposeDialogs.Core)
    implementation(Libs.ThirdParty.ComposeDialogs.Calendar)
    implementation(Libs.ThirdParty.ComposeDialogs.Clock)
    implementation(Libs.ThirdParty.ComposeDialogs.Color)
}