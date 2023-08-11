package plugin

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationPlugin: Plugin<Project> {

    override fun apply(project: Project) {

        with(project) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }
            extensions.configure<ApplicationExtension>() {
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

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }

                buildTypes {
                    release {
                        isMinifyEnabled = true
                        isShrinkResources = true
                        isDebuggable = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }

                    create("beta") {
                        isShrinkResources = true
                        isMinifyEnabled = true
                        isDebuggable = false
                    }

                    debug {
                        isShrinkResources = false
                        isMinifyEnabled = false
                        isDebuggable = true
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
}