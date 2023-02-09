plugins {
    id ("com.android.application")
    id ("kotlin-android")
    id ("kotlin-parcelize")
    id ("kotlin-kapt")
    id ("androidx.navigation.safeargs.kotlin")
    id ("dagger.hilt.android.plugin")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.hegunhee.newsimplememoapp"
        minSdk = 21
        targetSdk = 32
        versionCode = (1)
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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

    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":feature"))

    implementation(libs.bundles.ui)

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

    //Room DB
    implementation(libs.bundles.room)
    kapt(libs.room.compiler)

    androidTestImplementation(libs.room.testing)
    androidTestImplementation(libs.test.ext.junit)
    androidTestImplementation(libs.mockito.core)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)


}