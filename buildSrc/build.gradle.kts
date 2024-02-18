plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

repositories {
    maven("https://plugins.gradle.org/m2/")
    mavenCentral()
    mavenLocal()
    google()
}

dependencies {
    implementation(Dependencies.Kotlin.GradlePlugin)
    implementation(Dependencies.Android.GradlePlugin)
    implementation(Dependencies.SquareUp.JavaPoet)
    implementation("org.jlleitschuh.gradle:ktlint-gradle:12.0.2")
}

kotlin {
    sourceSets.getByName("main").kotlin.srcDir("buildSrc/src/main/kotlin")
}

//apply(plugin = "org.jlleitschuh.gradle.ktlint")