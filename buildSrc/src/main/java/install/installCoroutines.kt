package install

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.installCoroutines() {
    dependencies {
        add(Configuration.Implementation, Libs.Kotlin.Coroutines)
    }
}