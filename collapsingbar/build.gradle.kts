plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.vanniktech.maven.publish") version "0.34.0"
    id("com.gradleup.nmcp") version "1.2.0" apply false
    id("signing")
}
@Suppress("UnstableApiUsage")
android {
    namespace = "io.team2681.compose.collapsingbar"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    testOptions {
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.material3)
    implementation(libs.coil.compose)

    implementation(libs.androidx.compose.runtime)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.androidx.compose.material3)
    implementation(libs.compose.material.icons.core)
    implementation(libs.androidx.activity.compose)
    implementation(libs.kotlin.reflect)
    implementation(libs.activity.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
mavenPublishing {

    coordinates("io.github.team2681", "collapsingbar", "1.0.0")

    pom {
        name.set(project.properties["POM_NAME"] as String?)
        description.set(project.properties["POM_DESCRIPTION"] as String?)
        url.set(project.properties["POM_URL"] as String?)
        licenses {
            license()
            {
                name.set(project.properties["POM_LICENSE_NAME"] as String?)
                url.set(project.properties["POM_LICENSE_URL"] as String?)
            }
        }
        developers {
            developer {
                id.set(project.properties["POM_DEVELOPER_ID"] as String?)
                name.set(project.properties["POM_DEVELOPER_NAME"] as String?)
                email.set(project.properties["POM_DEVELOPER_EMAIL"] as String?)
                url.set(project.properties["POM_DEVELOPER_URL"] as String?)
            }
        }
        scm {
            connection.set(project.properties["POM_SCM_URL"] as String?)
            developerConnection.set(project.properties["POM_SCM_DEV_CONNECTION"] as String?)
            url.set(project.properties["POM_SCM_CONNECTION"] as String?)
        }
    }
}
signing {
    useGpgCmd()
    sign(publishing.publications)
}

tasks.withType<PublishToMavenRepository>().configureEach {
    dependsOn(tasks.withType<Sign>())
}
