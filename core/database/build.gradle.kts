plugins {
    id("library-setup")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.hellguy39.hellnotes.core.database"
    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}

dependencies {

    implementation(project(Modules.Core.Model))

    api(Dependencies.Room.RoomKtx)
    ksp(Dependencies.Room.RoomCompiler)

    implementation(Dependencies.Kotlin.Coroutines)

    implementation(Dependencies.Hilt.Android)
    ksp(Dependencies.Hilt.Compiler)

    implementation(Dependencies.SquareUp.Moshi)
    implementation(Dependencies.SquareUp.MoshiKotlin)
}
