package install

import util.Configuration
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
            kotlinCompilerExtensionVersion = Libs.Compose.CompilerVersion
        }
    }

    dependencies {
        add(Configuration.Implementation, Libs.Compose.Lifecycle)
        add(Configuration.Implementation, Libs.Compose.Activity)
        add(Configuration.Implementation, Libs.Compose.Ui)
        add(Configuration.Implementation, Libs.Compose.ToolingPreview)
        add(Configuration.Implementation, Libs.Compose.Material3)
        add(Configuration.Implementation, Libs.Compose.Material3WindowSizeClass)
        add(Configuration.Implementation, Libs.Compose.Navigation)
        add(Configuration.Implementation, Libs.Compose.LiveData)
    }
}