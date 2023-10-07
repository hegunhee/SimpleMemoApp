plugins {
    id ("hegunhee.android")
    id ("kotlin-android")
    id ("kotlin-parcelize")
    id ("kotlin-kapt")
    id ("androidx.navigation.safeargs.kotlin")
    id ("dagger.hilt.android.plugin")
}

android {

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