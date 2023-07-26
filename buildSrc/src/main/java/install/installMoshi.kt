package install

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import util.Configuration
import Libs

fun Project.installMoshi() {

    plugins.apply("com.google.devtools.ksp")

    dependencies {
        add(Configuration.Implementation, Libs.Moshi.Moshi)
        add(Configuration.Implementation, Libs.Moshi.MoshiKotlin)
    }
}