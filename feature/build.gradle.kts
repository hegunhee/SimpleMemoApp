plugins {
    id ("hegunhee.android")
    id ("kotlin-android")
    id ("kotlin-parcelize")
    id ("hegunhee.hilt")
    id ("androidx.navigation.safeargs.kotlin")
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
}