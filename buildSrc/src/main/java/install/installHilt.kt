package install

import Configuration
import Libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.installHilt() {

    plugins.apply("com.google.dagger.hilt.android")
    plugins.apply("org.jetbrains.kotlin.kapt")

    dependencies {
        add(Configuration.Implementation, Libs.Google.Hilt.Android)
        add(Configuration.Implementation, Libs.Google.Hilt.NavigationCompose)
        add(Configuration.Implementation, Libs.Google.Hilt.Work)

        add(Configuration.Kapt, Libs.Google.Hilt.Compiler)
        add(Configuration.Kapt, Libs.Google.Hilt.AndroidXCompiler)
    }
}