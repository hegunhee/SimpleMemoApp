plugins {
    id ("hegunhee.android")
}

android {
    namespace = "com.hegunhee.compose_feature.util"

}

dependencies {

    implementation(libs.annotation.jvm)

    implementation(project(":core:domain"))
}