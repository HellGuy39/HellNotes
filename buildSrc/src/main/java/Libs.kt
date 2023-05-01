object Libs {

    private const val junitVersion = "4.13.2"
    const val JUnit = "junit:junit:$junitVersion"

    object Gradle {
        private const val version = "7.4.2"
        const val Plugin = "com.android.tools.build:gradle:$version"
    }

    object SquareUp {

        private const val moshiVersion = "1.14.0"
        const val Moshi = "com.squareup.moshi:moshi:$moshiVersion"
        const val MoshiKotlin = "com.squareup.moshi:moshi-kotlin:$moshiVersion"
    }

    object Kotlin {

        private const val coroutinesVersion = "1.6.4"
        const val Coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"

//        private const val serializationVersion = "1.5.0"
//        const val Serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion"

        private const val plugineVersion = "1.8.20"
        const val Plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$plugineVersion"
        const val SerializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:$plugineVersion"

    }

    object AndroidX {

        private const val coreVersion = "1.9.0"
        private const val appCompatVersion = "1.7.0-alpha02"
        private const val lifecycleVersion = "2.5.1"
        private const val splashScreenVersion = "1.0.0"
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

        object DataStore {

            private const val version = "1.0.0"

            const val Preferences = "androidx.datastore:datastore-preferences:$version"
            const val Proto = "androidx.datastore:datastore:$version"

        }

        object Room {
            private const val version = "2.5.1"

            const val RoomKtx = "androidx.room:room-ktx:$version"
            const val RoomCompiler = "androidx.room:room-compiler:$version"
        }

        object Compose {

            private const val composeVersion = "1.4.2"
            private const val lifecycleVersion = "2.6.1"
            private const val navigationVersion = "2.5.3"
            private const val material3Version = "1.1.0-rc01"
            private const val activityVersion = "1.6.1"
            
            const val Lifecycle = "androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion"
            const val Activity = "androidx.activity:activity-compose:$activityVersion"
            const val Ui = "androidx.compose.ui:ui:$composeVersion"
            const val ToolingPreview = "androidx.compose.ui:ui-tooling-preview:$composeVersion"
            const val Material3 = "androidx.compose.material3:material3:$material3Version"
            const val Navigation = "androidx.navigation:navigation-compose:$navigationVersion"
            const val LiveData = "androidx.compose.runtime:runtime-livedata:$composeVersion"

            const val UiTestJUnit = "androidx.compose.ui:ui-test-junit4:$composeVersion"
            const val UiTooling = "androidx.compose.ui:ui-tooling:$composeVersion"
            const val UiTestManifest = "androidx.compose.ui:ui-test-manifest:$composeVersion"

        }

    }

    object Google {

        private const val materialVersion = "1.9.0-rc01"
        const val Material = "com.google.android.material:material:$materialVersion"

        private const val servicesPluginVersion = "4.3.15"
        const val ServicesPlugin = "com.google.gms:google-services:$servicesPluginVersion"

        object Accompanist {

            private const val version = "0.30.1"

            const val NavigationAnimation =
                "com.google.accompanist:accompanist-navigation-animation:$version"
            const val SystemUiController =
                "com.google.accompanist:accompanist-systemuicontroller:$version"
            const val Permissions = "com.google.accompanist:accompanist-permissions:$version"
            const val FlowLayout = "com.google.accompanist:accompanist-flowlayout:$version"
            const val Pager = "com.google.accompanist:accompanist-pager:$version"
            const val PagerIndicators =
                "com.google.accompanist:accompanist-pager-indicators:$version"

        }

        object Hilt {

            private const val version = "2.45"
            private const val navigationVersion = "1.0.0"

            const val Plugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
            const val Android = "com.google.dagger:hilt-android:$version"
            const val Compiler = "com.google.dagger:hilt-compiler:$version"
            const val NavigationCompose = "androidx.hilt:hilt-navigation-compose:$navigationVersion"
        }

        object Firebase {

            private const val crashlyticsVersion = "18.3.6"
            private const val analyticsVersion = "21.2.2"

            const val Crashlytics = "com.google.firebase:firebase-crashlytics-ktx:$crashlyticsVersion"
            const val Analytics = "com.google.firebase:firebase-analytics-ktx:$analyticsVersion"

            private const val crashlyticsPluginVersion = "2.9.5"
            const val CrashlyticsPlugin = "com.google.firebase:firebase-crashlytics-gradle:$crashlyticsPluginVersion"
        }

    }

    object Ktor {

        private const val version = "2.2.4"
        private const val logbackVersion = "1.4.6"

        const val Core = "io.ktor:ktor-client-core:$version"
        const val Android = "io.ktor:ktor-client-android:$version"
        //const val Serialization = "io.ktor:ktor-client-serialization:$version"
        const val ContentNegotiation = "io.ktor:ktor-client-content-negotiation:$version"
        const val JsonSerializer = "io.ktor:ktor-serialization-kotlinx-json:$version"
        const val Logging = "io.ktor:ktor-client-logging:$version"
        const val Logback = "ch.qos.logback:logback-classic:$logbackVersion"

    }

    object ThirdParty {

        object ComposeDialogs {
            private const val version = "1.1.0"

            const val Core = "com.maxkeppeler.sheets-compose-dialogs:core:$version"
            const val Calendar = "com.maxkeppeler.sheets-compose-dialogs:calendar:$version"
            const val Clock = "com.maxkeppeler.sheets-compose-dialogs:clock:$version"

        }

        object ReorderList {
            private const val version = "0.9.6"

            const val ComposeReorderableList =
                "org.burnoutcrew.composereorderable:reorderable:$version"
        }

        object ComposeRichText {
            private const val version = "0.16.0"

            const val RichTextUi = "com.halilibo.compose-richtext:richtext-ui:$version"
            const val RichTextCommonMark ="com.halilibo.compose-richtext:richtext-commonmark:$version"
            const val RichTextUiMaterial3 = "com.halilibo.compose-richtext:richtext-ui-material3:$version"

        }

    }
}