package install

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.installRoom() {

    plugins.apply("com.google.devtools.ksp")

    dependencies {
        add(Configuration.Api, Libs.AndroidX.Room.RoomKtx)
        add(Configuration.Ksp, Libs.AndroidX.Room.RoomCompiler)
    }
}