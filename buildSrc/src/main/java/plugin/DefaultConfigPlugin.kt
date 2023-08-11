package plugin

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class DefaultConfigPlugin: Plugin<Project> {

    override fun apply(project: Project) {

        project.pluginManager.apply("com.android.application")
        val androidExtension = project.extensions.getByType<ApplicationExtension>()

        androidExtension.apply {
            compileSdk = 34 // Android 14

            defaultConfig {
                minSdk = 29 // Android 10
                targetSdk = 34 // Android 14
                versionCode = 7
                versionName = "2.0" // X.Y.Z; X = Major, Y = minor, Z = Patch level

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                vectorDrawables {
                    useSupportLibrary = true
                }
            }

            flavorDimensions.add("type")

            productFlavors {

                productFlavors {
                    create("development") {
                        dimension = "type"
                    }
                    create("production") {
                        dimension = "type"
                    }
                }
            }
        }
    }
}