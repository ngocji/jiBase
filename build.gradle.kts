import com.android.build.gradle.BaseExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.hilt.gradle) apply false
}

allprojects {

    tasks.withType(KotlinCompile::class.java).configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    plugins.withType(com.android.build.gradle.BasePlugin::class.java).configureEach {
        project.extensions.getByType<BaseExtension>().apply {
            setCompileSdkVersion(34)

            defaultConfig {
                minSdk = 21
                targetSdk = 34
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
        }
    }
}

subprojects {

}