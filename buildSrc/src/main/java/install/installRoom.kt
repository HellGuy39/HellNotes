package install

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import util.Configuration
import Libs

fun Project.installRoom() {

    plugins.apply("com.google.devtools.ksp")

    dependencies {
        add(Configuration.Api, Libs.Room.RoomKtx)
        add(Configuration.Ksp, Libs.Room.RoomCompiler)
    }
}