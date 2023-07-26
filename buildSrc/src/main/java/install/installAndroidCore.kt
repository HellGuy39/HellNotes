package install

import util.Configuration
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import Libs

fun Project.installAndroidCore() {
    dependencies {
        add(Configuration.Implementation, Libs.AndroidX.CoreKtx)
        add(Configuration.Implementation, Libs.AndroidX.LifecycleKtx)
        add(Configuration.Implementation, Libs.AndroidX.AppCompat)
        add(Configuration.Implementation, Libs.AndroidX.WorkKtx)

        add(Configuration.DebugImplementation, Libs.LeakCanary.LeakCanaryAndroid)
    }
}