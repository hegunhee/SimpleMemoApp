plugins {
    id ("hegunhee.android")
    id ("kotlin-parcelize")
    id ("hegunhee.hilt")
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
}