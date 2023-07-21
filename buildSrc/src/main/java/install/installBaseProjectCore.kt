package install

import Modules
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import util.Configuration

fun Project.installBaseProjectCore() {
    dependencies {
        add(Configuration.Implementation, project(Modules.Core.Ui))
        add(Configuration.Implementation, project(Modules.Core.Domain))
        add(Configuration.Implementation, project(Modules.Core.Model))
        add(Configuration.Implementation, project(Modules.Core.Common))
    }
}