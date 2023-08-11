import org.gradle.kotlin.dsl.`kotlin-dsl`
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

gradlePlugin {
    plugins {
        register("hellnotes.android.application") {
            id = "hellnotes.android.application"
            implementationClass = "plugin.AndroidApplicationPlugin"
        }
        register("hellnotes.android.library") {
            id = "hellnotes.android.library"
            implementationClass = "plugin.AndroidLibraryPlugin"
        }
        register("hellnotes.android.feature") {
            id = "hellnotes.android.feature"
            implementationClass = "plugin.AndroidFeaturePlugin"
        }
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    //compileOnly(gradleApi())

    implementation("com.android.tools.build:gradle:8.1.0")
    implementation(kotlin("gradle-plugin", "1.9.0"))
//    compileOnly(kotlin("android-extensions"))
    implementation("com.squareup:javapoet:1.13.0")
}