plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.hilt.gradle)
    kotlin("kapt")
}


android {
    namespace = "comx.y.z.kotlinbase"
    compileSdk = 35

    defaultConfig {
        applicationId = "comx.y.z.kotlinbase"
        targetSdk = 35
        minSdk = 21
        versionCode = 1
        versionName = "4.2.9-pre3"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    signingConfigs {
//        create("sample") {
//            keyAlias = "com.ji.sample"
//            keyPassword = "12345@"
//            storeFile = file("sample.jsk")
//            storePassword = "12345@"
//        }
    }

    buildTypes {
        named("debug") {
//            signingConfig = signingConfigs.getByName("sample")
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            versionNameSuffix = "-debug"
        }

        named("release") {
//            signingConfig = signingConfigs.getByName("sample")
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-defaults.txt"),
                "proguard-rules.pro",
            )
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    applicationVariants.all(ApplicationVariantAction())
}

dependencies {
    // Module dependencies
    implementation(project(":jibase"))
    implementation(libs.bundles.androidX)
    implementation(libs.bundles.coroutine)

    implementation(libs.bundles.common)
    kapt(libs.bundles.common.compiler)

    implementation(libs.bundles.hilt)
    kapt(libs.bundles.hilt.compiler)
}

class ApplicationVariantAction : Action<com.android.build.gradle.api.ApplicationVariant> {
    override fun execute(variant: com.android.build.gradle.api.ApplicationVariant) {
        val fileName = createFileName()
        variant.outputs.all(VariantOutputAction(fileName))
    }

    private fun createFileName(): String {
        return "ExportBase.apk"
    }

    class VariantOutputAction(
        private val fileName: String
    ) : Action<com.android.build.gradle.api.BaseVariantOutput> {
        override fun execute(output: com.android.build.gradle.api.BaseVariantOutput) {
            if (output is com.android.build.gradle.internal.api.BaseVariantOutputImpl) {
                output.outputFileName = fileName
            }
        }
    }
}

