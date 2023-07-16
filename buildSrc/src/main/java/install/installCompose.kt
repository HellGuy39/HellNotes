package install

import Config
import Configuration
import Libs
import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.installCompose() {

    val androidExtension = project.extensions
        .getByName("android")

    if (androidExtension !is BaseExtension)
        return

    androidExtension.apply {
        buildFeatures.compose = true
        composeOptions {
            kotlinCompilerExtensionVersion = Config.composeCompiler
        }
    }

    dependencies {
        add(Configuration.Implementation, Libs.AndroidX.Compose.Lifecycle)
        add(Configuration.Implementation, Libs.AndroidX.Compose.Activity)
        add(Configuration.Implementation, Libs.AndroidX.Compose.Ui)
        add(Configuration.Implementation, Libs.AndroidX.Compose.ToolingPreview)
        add(Configuration.Implementation, Libs.AndroidX.Compose.Material3)
        add(Configuration.Implementation, Libs.AndroidX.Compose.Navigation)
        add(Configuration.Implementation, Libs.AndroidX.Compose.LiveData)
    }
}