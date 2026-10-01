plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)

    // Maven İçin
    id("com.vanniktech.maven.publish") version "0.37.0"
}

android {
    namespace = "com.toktasoft.animatedconnectionobserver"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation(platform(libs.androidx.bom))
    implementation(libs.androidx.material3)

    // icon paketi
    implementation(libs.androidx.material.icons.extended)
}
 /* ****************************************   Maven İçin   ************************************ */
mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    coordinates(
        groupId = "com.toktasoft",
        artifactId = "animated-connection-observer",
        version = "1.0.1"
    )

    pom {
        name.set("Animated Connection Observer")

        description.set(
            "A Jetpack Compose library for observing and displaying network connectivity changes with animated UI components."
        )

        url.set("GITHUB_REPO_URL")

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
                name.set("Mustafa Toktaş")
                url.set("https://github.com/mustafatoktas")
            }
        }

        scm {
            url.set("GITHUB_REPO_URL")
            connection.set("scm:git:GITHUB_REPO_GIT_URL")
            developerConnection.set("scm:git:GITHUB_REPO_GIT_URL")
        }
    }
}