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

    implementation(project(":domain"))
    implementation(project(":common_resource"))

}