import com.hellguy39.hellnotes.HellNotesBuildType
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName
import java.util.Properties

plugins {
    id("hellnotes.android.application")
    id("hellnotes.android.application.compose")
    id("hellnotes.hilt")
    id("hellnotes.android.room")
    id("hellnotes.android.application.firebase")
    id("hellnotes.android.application.flavors")
    alias(libs.plugins.baselineprofile)
}

val signingProperties = readProperties(file("signing.properties"))

fun readProperties(propertiesFile: File) =
    Properties().apply {
        propertiesFile.inputStream().use { fileInputStream -> load(fileInputStream) }
    }

android {
    namespace = "com.hellguy39.hellnotes"

    signingConfigs {
        getByName("debug") {
            keyAlias = signingProperties["KEY_ALIAS"].toString()
            keyPassword = signingProperties["KEY_PASSWORD"].toString()
            storeFile = file("keystore")
            storePassword = signingProperties["KEYSTORE_PASSWORD"].toString()
        }
        create("release") {
            keyAlias = signingProperties["KEY_ALIAS"].toString()
            keyPassword = signingProperties["KEY_PASSWORD"].toString()
            storeFile = file("keystore")
            storePassword = signingProperties["KEYSTORE_PASSWORD"].toString()
        }
    }

    defaultConfig {
        applicationId = Configuration.applicationId
        minSdk = Configuration.minSdk
        targetSdk = Configuration.targetSdk
        versionCode = Configuration.versionCode
        versionName = Configuration.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }

        archivesName.set("HellNotes v$versionName")
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("debug")
            applicationIdSuffix = HellNotesBuildType.DEBUG.applicationIdSuffix
        }
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            applicationIdSuffix = HellNotesBuildType.RELEASE.applicationIdSuffix
            signingConfig = signingConfigs.getByName("release")

            // Ensure Baseline Profile is fresh for release builds.
            baselineProfile.automaticGenerationDuringBuild = true
        }
    }

    packaging {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        resources.excludes.add("/META-INF/INDEX.LIST")
    }
}

dependencies {
    implementation(projects.feature.onBoarding)
    implementation(projects.feature.lock)
    implementation(projects.feature.search)
    implementation(projects.feature.home)
    implementation(projects.feature.labelEdit)
    implementation(projects.feature.noteDetail)
    implementation(projects.feature.reminderEdit)
    implementation(projects.feature.aboutApp)
    implementation(projects.feature.settings)
    implementation(projects.feature.lockSelection)
    implementation(projects.feature.languageSelection)
    implementation(projects.feature.lockSetup)
    implementation(projects.feature.labelSelection)
    implementation(projects.feature.noteStyleEdit)
    implementation(projects.feature.noteSwipeEdit)
    implementation(projects.feature.backup)

    implementation(projects.core.ui)
    implementation(projects.core.data)
    implementation(projects.core.common)
    implementation(projects.core.domain)
    implementation(projects.core.database)
    implementation(projects.core.datastore)
    implementation(projects.core.storeApi)
    implementation(projects.core.model)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.android)
    androidTestImplementation(libs.androidx.test.espresso.core)

    baselineProfile(projects.benchmark)
}

baselineProfile {
    // Don't build on every iteration of a full assemble.
    // Instead enable generation directly for the release build variant.
    automaticGenerationDuringBuild = false

    // Make use of Dex Layout Optimizations via Startup Profiles
    dexLayoutOptimization = true
}
