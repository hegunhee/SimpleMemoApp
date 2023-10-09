plugins {
    id ("hegunhee.application")
    id ("kotlin-android")
    id ("kotlin-parcelize")
    id ("kotlin-kapt")
    id ("androidx.navigation.safeargs.kotlin")
}

android {

}

dependencies {

    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":feature"))
    implementation(project(":common_resource"))

}