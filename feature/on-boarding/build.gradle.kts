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
    namespace = "com.hellguy39.hellnotes.feature.on_boarding"
}

installBaseProjectCore()
installAndroidCore()
installCoroutines()
installAccompanist()
installCompose()
installHilt()