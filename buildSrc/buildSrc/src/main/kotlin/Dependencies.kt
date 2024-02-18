object Dependencies {

    object Kotlin {

        private const val kotlinVersion = "1.9.22"
        const val GradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val GradleSerializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion"

        private const val coroutinesVersion = "1.8.0"
        const val Coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"

    }

    object Android {
        const val GradlePlugin = "com.android.tools.build:gradle:8.2.2"
    }

    private const val junitVersion = "4.13.2"
    const val JUnit = "junit:junit:$junitVersion"

    object RuStore {
        const val AppUpdate = "ru.rustore.sdk:appupdate:1.0.1"
    }

    object SquareUp {

        private const val moshiVersion = "1.15.1"
        const val Moshi = "com.squareup.moshi:moshi:$moshiVersion"
        const val MoshiKotlin = "com.squareup.moshi:moshi-kotlin:$moshiVersion"

        const val JavaPoet = "com.squareup:javapoet:1.13.0"
    }

    object AndroidX {

        private const val coreVersion = "1.12.0"
        private const val appCompatVersion = "1.6.1"
        private const val lifecycleVersion = "2.6.2"
        private const val splashScreenVersion = "1.0.1"
        private const val biometricVersion = "1.2.0-alpha05"
        private const val profileInstallerVersion = "1.3.0"
        private const val workVersion = "2.8.1"

        const val ProfileInstaller = "androidx.profileinstaller:profileinstaller:$profileInstallerVersion"

        const val SplashScreen ="androidx.core:core-splashscreen:$splashScreenVersion"
        const val Biometric = "androidx.biometric:biometric-ktx:$biometricVersion"

        const val CoreKtx = "androidx.core:core-ktx:$coreVersion"
        const val LifecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
        const val AppCompat = "androidx.appcompat:appcompat:$appCompatVersion"
        const val WorkKtx = "androidx.work:work-runtime-ktx:$workVersion"

        private const val junitVersion = "1.1.5"
        private const val espressoVersion = "3.5.1"

        const val JUnit = "androidx.test.ext:junit:$junitVersion"
        const val Espresso = "androidx.test.espresso:espresso-core:$espressoVersion"

    }

    object DataStore {
        private const val version = "1.0.0"

        const val Preferences = "androidx.datastore:datastore-preferences:$version"
        const val Proto = "androidx.datastore:datastore:$version"
    }

    object Room {
        private const val version = "2.6.1"

        const val RoomKtx = "androidx.room:room-ktx:$version"
        const val RoomCompiler = "androidx.room:room-compiler:$version"
    }

    object Compose {
        private const val composeVersion = "1.6.1"
        private const val lifecycleVersion = "2.7.0"
        private const val navigationVersion = "2.7.6"
        private const val material3Version = "1.2.0"
        private const val activityVersion = "1.8.2"

        const val Lifecycle = "androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion"
        const val Activity = "androidx.activity:activity-compose:$activityVersion"
        const val Ui = "androidx.compose.ui:ui:$composeVersion"
        const val ToolingPreview = "androidx.compose.ui:ui-tooling-preview:$composeVersion"
        const val Material3 = "androidx.compose.material3:material3:$material3Version"
        const val Material3WindowSizeClass = "androidx.compose.material3:material3-window-size-class:$material3Version"
        const val Material3Adaptive = "androidx.compose.material3:material3-adaptive:1.0.0-alpha06"
        const val Material3AdaptiveNavigationSuite = "androidx.compose.material3:material3-adaptive-navigation-suite:1.0.0-alpha03"
        const val Navigation = "androidx.navigation:navigation-compose:$navigationVersion"
        const val LiveData = "androidx.compose.runtime:runtime-livedata:$composeVersion"

        const val UiTestJUnit = "androidx.compose.ui:ui-test-junit4:$composeVersion"
        const val UiTooling = "androidx.compose.ui:ui-tooling:$composeVersion"
        const val UiTestManifest = "androidx.compose.ui:ui-test-manifest:$composeVersion"
    }

    object Google {
        const val Material = "com.google.android.material:material:1.11.0"

        const val GradleServicesPlugin = "com.google.gms:google-services:4.4.1"
    }


    object Accompanist {

        private const val version = "0.34.0"

        const val Permissions = "com.google.accompanist:accompanist-permissions:$version"
        const val Adaptive = "com.google.accompanist:accompanist-adaptive:$version"
    }


    object Hilt {

        private const val version = "2.50"
        private const val navigationVersion = "1.1.0"
        private const val workVersion = "1.1.0"
        private const val androidXCompilerVersion = "1.1.0"

        const val GradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"

        const val Android = "com.google.dagger:hilt-android:$version"
        const val AndroidXCompiler = "androidx.hilt:hilt-compiler:$androidXCompilerVersion"
        const val Compiler = "com.google.dagger:hilt-compiler:$version"
        const val NavigationCompose = "androidx.hilt:hilt-navigation-compose:$navigationVersion"
        const val Work = "androidx.hilt:hilt-work:$workVersion"
    }

    object Firebase {

        private const val crashlyticsVersion = "18.6.2"
        private const val analyticsVersion = "21.5.1"

        const val Crashlytics = "com.google.firebase:firebase-crashlytics-ktx:$crashlyticsVersion"
        const val Analytics = "com.google.firebase:firebase-analytics-ktx:$analyticsVersion"

        private const val crashlyticsPluginVersion = "2.9.9"
        const val CrashlyticsPlugin = "com.google.firebase:firebase-crashlytics-gradle:$crashlyticsPluginVersion"
    }

    object Ktor {

        private const val version = "2.3.8"

        const val Core = "io.ktor:ktor-client-core:$version"
        const val AndroidEngine = "io.ktor:ktor-client-android:$version"
        const val OkHttpEngine = "io.ktor:ktor-client-okhttp:$version"
        const val ContentNegotiation = "io.ktor:ktor-client-content-negotiation:$version"
        const val JsonSerializer = "io.ktor:ktor-serialization-kotlinx-json:$version"
        const val Logging = "io.ktor:ktor-client-logging:$version"

    }

    object ThirdParty {

        object ComposeDialogs {
            private const val version = "1.1.0"

            const val Core = "com.maxkeppeler.sheets-compose-dialogs:core:$version"
            const val Calendar = "com.maxkeppeler.sheets-compose-dialogs:calendar:$version"
            const val Clock = "com.maxkeppeler.sheets-compose-dialogs:clock:$version"

        }

        object ComposeRichText {
            private const val version = "0.16.0"

            const val RichTextUi = "com.halilibo.compose-richtext:richtext-ui:$version"
            const val RichTextCommonMark ="com.halilibo.compose-richtext:richtext-commonmark:$version"
            const val RichTextUiMaterial3 = "com.halilibo.compose-richtext:richtext-ui-material3:$version"

        }
    }
}