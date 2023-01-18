import org.gradle.internal.impldep.com.google.api.services.storage.model.Bucket.Lifecycle
import java.awt.SplashScreen

object Libs {

    private const val junitVersion = "4.13.2"
    const val JUnit = "junit:junit:$junitVersion"

    object SquareUp {

        private const val moshiVersion = "1.14.0"
        const val Moshi = "com.squareup.moshi:moshi:$moshiVersion"

    }

    object KotlinX {

        private val coroutinesVersion = "1.6.4"
        val Coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"

    }

    object AndroidX {

        private const val coreVersion = "1.9.0"
        private const val appCompatVersion = "1.7.0-alpha01"
        private const val lifecycleVersion = "2.5.1"
        private const val splashScreenVersion = "1.0.0"
        private const val biometricVersion = "1.2.0-alpha05"

        const val SplashScreen ="androidx.core:core-splashscreen:$splashScreenVersion"
        const val Biometric = "androidx.biometric:biometric-ktx:$biometricVersion"
        const val CoreKtx = "androidx.core:core-ktx:$coreVersion"
        const val LifecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
        const val AppCompat = "androidx.appcompat:appcompat:$appCompatVersion"

        private const val junitVersion = "1.1.5"
        private const val espressoVersion = "3.5.1"

        const val JUnit = "androidx.test.ext:junit:$junitVersion"
        const val Espresso = "androidx.test.espresso:espresso-core:$espressoVersion"

        object Room {
            private const val version = "2.5.0"

            const val RoomKtx = "androidx.room:room-ktx:$version"
            const val RoomCompiler = "androidx.room:room-compiler:$version"
        }

        object Compose {

            private const val composeVersion = "1.3.3"

            const val Lifecycle = "androidx.lifecycle:lifecycle-runtime-compose:2.6.0-alpha04"
            const val Activty = "androidx.activity:activity-compose:1.6.1"
            const val Ui = "androidx.compose.ui:ui:$composeVersion"
            const val ToolingPreview = "androidx.compose.ui:ui-tooling-preview:$composeVersion"
            const val Material3 = "androidx.compose.material3:material3:1.1.0-alpha04"
            const val Navigation = "androidx.navigation:navigation-compose:2.5.3"

            const val UiTestJUnit = "androidx.compose.ui:ui-test-junit4:$composeVersion"
            const val UiTooling = "androidx.compose.ui:ui-tooling:$composeVersion"
            const val UiTestManifest = "androidx.compose.ui:ui-test-manifest:$composeVersion"

        }

    }

    object Google {

        private const val materialVersion = "1.7.0"
        const val Material = "com.google.android.material:material:$materialVersion"

        object Accompanist {

            private const val version = "0.28.0"

            const val NavigationAnimation =
                "com.google.accompanist:accompanist-navigation-animation:$version"
            const val SystemUiController =
                "com.google.accompanist:accompanist-systemuicontroller:$version"
            const val Permissions = "com.google.accompanist:accompanist-permissions:$version"
            const val FlowLayout = "com.google.accompanist:accompanist-flowlayout:$version"

        }

        object Hilt {

            private const val version = "2.44.2"

            const val Android = "com.google.dagger:hilt-android:$version"
            const val Compiler = "com.google.dagger:hilt-compiler:$version"
            const val NavigationCompsoe= "androidx.hilt:hilt-navigation-compose:1.0.0"
        }

    }

    object MaxKeppler {

        object ComposeDialogs {
            private const val version = "1.0.2"

            const val Core = "com.maxkeppeler.sheets-compose-dialogs:core:$version"
            const val Calendar = "com.maxkeppeler.sheets-compose-dialogs:calendar:$version"
            const val Clock = "com.maxkeppeler.sheets-compose-dialogs:clock:$version"

        }

    }

}