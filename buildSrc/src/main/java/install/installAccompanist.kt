package install

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import util.Configuration
import Libs

fun Project.installAccompanist() {
    dependencies {
        add(Configuration.Implementation, Libs.Accompanist.Pager)
        add(Configuration.Implementation, Libs.Accompanist.PagerIndicators)
        add(Configuration.Implementation, Libs.Accompanist.Adaptive)
        add(Configuration.Implementation, Libs.Accompanist.NavigationAnimation)
        add(Configuration.Implementation, Libs.Accompanist.SystemUiController)
        add(Configuration.Implementation, Libs.Accompanist.Permissions)
        add(Configuration.Implementation, Libs.Accompanist.FlowLayout)
    }
}