package plugin

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidFeaturePlugin: Plugin<Project> {

    override fun apply(project: Project) {

        project.pluginManager.apply("com.android.library")
        project.pluginManager.apply("org.jetbrains.kotlin.android")
        val androidExtension = project.extensions.getByType<LibraryExtension>()

        androidExtension.apply {
            compileSdk = 34 // Android 14

            defaultConfig {
                minSdk = 29 // Android 10

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                vectorDrawables {
                    useSupportLibrary = true
                }
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            buildTypes {
                release {
                    isMinifyEnabled = true
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }

                create("beta") {
                    isMinifyEnabled = true
                }

                debug {
                    isMinifyEnabled = false
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