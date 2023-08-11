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
    namespace = "com.hellguy39.hellnotes.feature.privacy_policy"
}

installBaseProjectCore()
installAndroidCore()
installCoroutines()
installAccompanist()
installCompose()
installHilt()

dependencies {
    implementation(Libs.ThirdParty.ComposeRichText.RichTextUiMaterial3)
    implementation(Libs.ThirdParty.ComposeRichText.RichTextCommonMark)
}