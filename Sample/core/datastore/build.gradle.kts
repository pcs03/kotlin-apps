plugins {
    alias(libs.plugins.sample.android.library)
    alias(libs.plugins.sample.buildtype.library)
    alias(libs.plugins.sample.koin.android)
}

android {
    namespace = "nl.pcstet.core.datastore"
}

dependencies {
    implementation(projects.sample.core.model)
    api(libs.androidx.datastore)
    api(libs.androidx.datastore.preferences)
}