plugins {
    alias(libs.plugins.android.application)

    // compose
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.toktasoft.animatedconnectionobserver.sample"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.toktasoft.animatedconnectionobserver.sample"
        minSdk = 33
        targetSdk = 37
        versionCode = 3              // her release'te artır
        versionName = "1.0.2"        // her release'te artır

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                isMinifyEnabled = true
                isShrinkResources = true
                isDebuggable = false
                // The app's own rules live in src/main/keepRules, which AGP picks up by itself.
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
            }
        }
    }
    packaging {
        // The sample is shared as a downloadable APK, so a smaller file matters more than
        // the slightly faster start of uncompressed dex.
        dex {
            useLegacyPackaging = true
        }
        resources {
            // Version markers and Kotlin reflection metadata; nothing in the app reads them.
            excludes += setOf("/META-INF/*.version", "/kotlin/**", "DebugProbesKt.bin")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }
    buildFeatures {
        compose = true
    }
    androidResources {
        // Only English and Turkish ship. Library translations for other languages are dropped.
        // English is the default; a phone set to Turkish opens the app in Turkish.
        localeFilters += listOf("en", "tr")
        generateLocaleConfig = true
    }
}

dependencies {
    // modül bağımlılıkları
    implementation(project(":animatedconnectionobserver"))

    implementation(platform(libs.androidx.bom))
    implementation(libs.androidx.activity)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)

    // splash screen
    implementation(libs.androidx.core.splashscreen)
}
