package install

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.installMoshi() {

    plugins.apply("com.google.devtools.ksp")

    dependencies {
        add(Configuration.Implementation, Libs.SquareUp.Moshi)
        add(Configuration.Implementation, Libs.SquareUp.MoshiKotlin)
    }
}