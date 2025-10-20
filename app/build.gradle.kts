plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    // compose
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.toktasoft.animatedconnectionobserver"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.toktasoft.animatedconnectionobserver"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // modül bağımlılıkları
    implementation(project(":animated-connection-observer"))

    // compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)

    // compose activity
    implementation(libs.androidx.activity.compose)

    // icons
    implementation(libs.androidx.material.icons.extended)

    // splash screen
    implementation(libs.androidx.core.splashscreen)
}
