plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    // compose
    alias(libs.plugins.kotlin.compose)

    // publish için
    id("org.jetbrains.dokka") version "1.9.20"
    id("maven-publish")
    id("signing")
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

    // publishing
    // AGP, yayınlanabilir "release" component’ini buradan üretir
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
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

// Dokka javadoc jar (gerçek dokümantasyonu bununla ekliyoruz)
tasks.register<Jar>("dokkaJavadocJar") {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.get().outputDirectory)
    archiveClassifier.set("javadoc")
}

publishing {
    repositories {
        maven {
            name = "staging"
            url = layout.buildDirectory.dir("staging-repo").get().asFile.toURI()
        }
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                artifact(tasks["dokkaJavadocJar"])
                artifactId = "animated-connection-observer"

                pom {
                    name.set("Animated Connection Observer")
                    description.set("A lifecycle-aware, animated connectivity indicator built with Jetpack Compose.")
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
                    }
                    scm {
                        url.set("https://github.com/mustafatoktas/A.LIB_AnimatedConnectionObserver")
                        connection.set("scm:git:https://github.com/mustafatoktas/A.LIB_AnimatedConnectionObserver.git")
                        developerConnection.set("scm:git:ssh://git@github.com/mustafatoktas/A.LIB_AnimatedConnectionObserver.git")
                    }
                }
            }
        }
    }

    // Publication'ı oluşturduktan sonra imzala
    signing {
        publishing.publications.named("release").configure { sign(this) }
    }
}

signing {
    // GPG komutunu kullan
    useGpgCmd()
}
