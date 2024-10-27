plugins {
    id ("hegunhee.android")
    id ("hegunhee.view.feature")
    id ("kotlin-android")
    id ("kotlin-parcelize")
    id ("androidx.navigation.safeargs.kotlin")
}

android {

}

dependencies {

    implementation(project(":core:domain"))
    implementation(project(":common_resource"))

    implementation(project(":core:util"))

    debugImplementation(libs.fragment.test.manifest)
    androidTestImplementation(libs.fragment.test)
}