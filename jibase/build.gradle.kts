plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.hilt.gradle)
    kotlin("kapt")
}

android {
    namespace = "com.jibase"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.bundles.androidX)
    implementation(libs.bundles.coroutine)
    implementation(libs.bundles.hilt)
    kapt(libs.bundles.hilt.compiler)
    implementation(libs.bundles.common)
    kapt(libs.bundles.common.compiler)
}