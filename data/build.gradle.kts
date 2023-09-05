plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")

    id ("kotlin-parcelize")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":domain"))


    implementation(libs.core.ktx)

    // Coroutines
    implementation(libs.bundles.coroutine)
    implementation(libs.coroutine.test)

    //Room DB
    implementation(libs.bundles.room)
    kapt(libs.room.compiler)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)


}