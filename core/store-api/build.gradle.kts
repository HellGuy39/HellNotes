plugins {
    id("hellnotes.android.library")
    id("hellnotes.hilt")
}

android {
    namespace = "com.hellguy39.hellnotes.core.storeapi"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.domain)
    implementation(projects.core.model)

    implementation(libs.bundles.datastore)

    implementation(libs.kotlin.coroutines.play.services)

    implementation(libs.rustore.appupdate)
    implementation(libs.rustore.review)

    implementation(libs.googlePlay.appupdate)
    implementation(libs.googlePlay.appupdate.ktx)
    implementation(libs.googlePlay.review)
    implementation(libs.googlePlay.review.ktx)
}
