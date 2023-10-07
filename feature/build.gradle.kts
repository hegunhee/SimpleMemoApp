plugins {
    id("com.android.library")
    id ("kotlin-android")
    id ("kotlin-parcelize")
    id ("kotlin-kapt")
    id ("androidx.navigation.safeargs.kotlin")
    id ("dagger.hilt.android.plugin")
}

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures{
        viewBinding = true
        dataBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-alpha07"
    }
}

dependencies {

    implementation(project(":domain"))
    implementation(libs.bundles.ui)

    implementation(libs.fragment.ktx)

    implementation(libs.bundles.compose)

    implementation(libs.bundles.navigation)

    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    testImplementation(libs.mockito.inline)

    implementation(libs.bundles.lifecycle)

    // Coroutines
    implementation(libs.bundles.coroutine)
    implementation(libs.coroutine.test)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}