plugins {
    alias(libs.plugins.sample.android.library)
}

android {
    namespace = "nl.pcstet.core.datastore"
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    api(libs.androidx.datastore)
    api(libs.androidx.datastore.preferences)
}