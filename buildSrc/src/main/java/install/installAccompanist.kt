package install

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.installAccompanist() {
    dependencies {
        add(Configuration.Implementation, Libs.Google.Accompanist.Adaptive)
        add(Configuration.Implementation, Libs.Google.Accompanist.NavigationAnimation)
        add(Configuration.Implementation, Libs.Google.Accompanist.SystemUiController)
        add(Configuration.Implementation, Libs.Google.Accompanist.Permissions)
        add(Configuration.Implementation, Libs.Google.Accompanist.FlowLayout)
    }
}