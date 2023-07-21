package install

import util.Configuration
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import Libs

fun Project.installTestingTools() {
    dependencies {
        add(Configuration.AndroidTestImplementation, Libs.Testing.Work)
        add(Configuration.AndroidTestImplementation, Libs.Testing.UiTestJUnit)
        add(Configuration.DebugImplementation, Libs.Testing.UiTooling)
        add(Configuration.DebugImplementation, Libs.Testing.UiTestManifest)
        add(Configuration.TestImplementation, Libs.Testing.JUnit)
        add(Configuration.AndroidTestImplementation, Libs.Testing.AndroidJUnit)
        add(Configuration.AndroidTestImplementation, Libs.Testing.Espresso)
    }
}