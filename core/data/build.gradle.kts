import install.installCoroutines
import install.installHilt

plugins {
    id(ProjectPlugin.Library)
}

android {
    namespace = "com.hellguy39.hellnotes.core.data"
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