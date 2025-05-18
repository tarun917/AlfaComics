import org.gradle.kotlin.dsl.implementation

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose") // Apply the Compose Compiler plugin
}

android {
    namespace = "com.alfacomics"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.alfacomics"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)  // Update to the latest version (e.g., 1.13.1)
    implementation(libs.androidx.lifecycle.runtime.ktx)  // Update to the latest version (e.g., 2.8.6)
    implementation(libs.androidx.activity.compose)  // Update to the latest version (e.g., 1.9.2)
    implementation(platform(libs.androidx.compose.bom))  // Update to the latest version (e.g., 2025.06.00)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)  // Update to the latest version (e.g., 2.7.7)
    implementation(libs.coil.compose)  // Update to the latest version (e.g., 2.7.0)
    implementation(libs.androidx.material3.android)  // For image loading
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.annotation)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.junit)  // Update to the latest version (e.g., 4.13.2)
    androidTestImplementation(libs.androidx.junit)  // Update to the latest version (e.g., 1.2.1)
    androidTestImplementation(libs.androidx.espresso.core)  // Update to the latest version (e.g., 3.6.1)
    androidTestImplementation(platform(libs.compose.bom))
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.compose.material.material.icons.extended)
    implementation(libs.material.icons.extended)  // For Group icon
    implementation(libs.androidx.navigation.compose.v290)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.material.material.icons.extended2)  // Use the latest version compatible with your Compose setup
    //noinspection BomWithoutPlatform
    androidTestImplementation(libs.compose.bom)
    implementation(platform(libs.androidx.compose.bom.v20240903))
}