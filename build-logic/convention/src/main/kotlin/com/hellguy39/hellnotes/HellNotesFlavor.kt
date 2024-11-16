package com.hellguy39.hellnotes

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor

@Suppress("EnumEntryName")
enum class FlavorDimension {
    contentType
}

@Suppress("EnumEntryName")
enum class AppStore {
    RuStore,
    PlayStore,
    None,
}

// The content for the app can either come from local static data which is useful for demo
// purposes, or from a production backend server which supplies up-to-date, real content.
// These two product flavors reflect this behaviour.
@Suppress("EnumEntryName")
enum class HellNotesFlavor(
    val dimension: FlavorDimension,
    val applicationIdSuffix: String? = null,
    val appStore: AppStore? = null
) {
    demo(FlavorDimension.contentType, applicationIdSuffix = ".demo"),
    demoRuStore(FlavorDimension.contentType, appStore = AppStore.RuStore),
    demoPlayStore(FlavorDimension.contentType, appStore = AppStore.PlayStore),

    prod(FlavorDimension.contentType, appStore = AppStore.None),
    prodRuStore(FlavorDimension.contentType, appStore = AppStore.RuStore),
    prodPlayStore(FlavorDimension.contentType, appStore = AppStore.PlayStore)
}

fun configureFlavors(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    flavorConfigurationBlock: ProductFlavor.(flavor: HellNotesFlavor) -> Unit = {}
) {
    commonExtension.apply {
        flavorDimensions += FlavorDimension.contentType.name
        productFlavors {
            HellNotesFlavor.values().forEach {
                create(it.name) {
                    dimension = it.dimension.name
                    flavorConfigurationBlock(this, it)
                    if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
                        if (it.applicationIdSuffix != null) {
                            applicationIdSuffix = it.applicationIdSuffix
                        }
                    }
                    buildConfigField("String", "APP_STORE", "\"${it.appStore}\"")
                }
            }
        }
    }
}