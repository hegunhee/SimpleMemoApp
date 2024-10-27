plugins {
    id ("hegunhee.android")
    id ("kotlin-parcelize")
    id ("hegunhee.hilt")
    id ("org.jetbrains.kotlin.plugin.serialization")
}
android {
    namespace = "com.hegunhee.newsimplememoapp.data"
}
dependencies {

    implementation(project(":core:domain"))


    implementation(libs.core.ktx)

    // Coroutines
    implementation(libs.bundles.coroutine)
    testImplementation(libs.coroutine.test)

    //Room DB
    implementation(libs.bundles.room)
    kapt(libs.room.compiler)

    implementation(libs.bundles.retrofit)

    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.ext.junit)

    testImplementation(libs.mockito.kotlin)
}
