package install

import util.Configuration
import Libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.installHilt() {

    plugins.apply("com.google.dagger.hilt.android")
    plugins.apply("org.jetbrains.kotlin.kapt")

    dependencies {
        add(Configuration.Implementation, Libs.Hilt.Android)
        add(Configuration.Implementation, Libs.Hilt.NavigationCompose)
        add(Configuration.Implementation, Libs.Hilt.Work)

        add(Configuration.Kapt, Libs.Hilt.Compiler)
        add(Configuration.Kapt, Libs.Hilt.AndroidXCompiler)
    }
}