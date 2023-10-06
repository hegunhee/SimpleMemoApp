plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")

    id ("kotlin-parcelize")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
}

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
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