plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)

    // Maven İçin
    alias(libs.plugins.vanniktech.maven.publish)
}

group = "com.toktasoft"
version = "1.0.2"               // her release'te artır

android {
    namespace = "com.toktasoft.animatedconnectionobserver"
    // Every library resource starts with this prefix so it never clashes with the host app.
    resourcePrefix = "animated_connection_observer_"
    compileSdk {
        version = release(37)
    }
    defaultConfig {
        minSdk = 33
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    api(platform(libs.androidx.bom))
    implementation(libs.androidx.material3)
}
 /* ****************************************   Maven İçin   ************************************ */
mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    coordinates(
        groupId = "com.toktasoft",
        artifactId = "animated-connection-observer",
        version = version.toString(),
    )

    pom {
        name.set("Animated Connection Observer")
        description.set(
            "A Jetpack Compose library for observing and displaying network connectivity changes with animated UI components."
        )
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
            }
            developer {
                id.set("toktasoft")
                name.set("Toktasoft")
                url.set("https://github.com/Toktasoft")
            }
        }
        organization {
            name.set("Toktasoft")
            url.set("https://toktasoft.com")
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