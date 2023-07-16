package install

import Configuration
import Libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.installFirebase() {

    plugins.apply("com.google.gms.google-services")
    plugins.apply("com.google.firebase.crashlytics")

    dependencies {
        add(Configuration.Implementation, Libs.Google.Firebase.Analytics)
        add(Configuration.Implementation, Libs.Google.Firebase.Crashlytics)
    }
}