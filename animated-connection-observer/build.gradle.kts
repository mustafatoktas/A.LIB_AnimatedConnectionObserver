plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    // compose
    alias(libs.plugins.kotlin.compose)

    // vanniktech maven publish
    alias(libs.plugins.vanniktech.maven.publish)

    // dokka
    alias(libs.plugins.dokka)
}

group = "com.toktasoft"
version = "1.0.0"               // her release’te artır

android {
    namespace = "com.toktasoft.animatedconnectionobserver"
    compileSdk = 36

    defaultConfig {
        minSdk = 28
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    // compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)

    // lifecycle
    implementation(libs.androidx.lifecycle.runtime.compose)

    // icons
    implementation(libs.androidx.material.icons.extended)
}

mavenPublishing {
    coordinates("com.toktasoft", "animated-connection-observer", version.toString())

    pom {
        name.set("Animated Connection Observer")
        description.set("A lifecycle-aware, animated connectivity indicator built with Jetpack Compose.")
        inceptionYear.set("2025")
        url.set("https://github.com/mustafatoktas/A.LIB_AnimatedConnectionObserver")
        licenses {
            license {
                name.set("GNU General Public License v3.0")
                url.set("https://www.gnu.org/licenses/gpl-3.0.html")
                distribution.set("repo")
            }
        }
        developers {
            developer {
                id.set("mustafatoktas")
                name.set("Mustafa TOKTAŞ")
                url.set("https://toktasoft.com")
                email.set("info@mustafatoktas.com")
            }
        }
        scm {
            url.set("https://github.com/mustafatoktas/A.LIB_AnimatedConnectionObserver")
            connection.set("scm:git:https://github.com/mustafatoktas/A.LIB_AnimatedConnectionObserver.git")
            developerConnection.set("scm:git:ssh://git@github.com/mustafatoktas/A.LIB_AnimatedConnectionObserver.git")
        }
        issueManagement {
            system.set("GitHub Issues")
            url.set("https://github.com/mustafatoktas/A.LIB_AnimatedConnectionObserver/issues")
        }
    }
}
