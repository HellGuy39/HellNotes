package install

import Libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import util.Configuration

fun Project.installCoroutines() {
    dependencies {
        add(Configuration.Implementation, Libs.Kotlin.Coroutines)
    }
}