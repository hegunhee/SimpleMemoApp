plugins {
    id("hegunhee.compose")
}

android {
    namespace = "com.hegunhee.newsimplememoapp.core.ui"
}

dependencies {

    implementation(libs.compose.bottomsheetdialog)

    implementation(project(":core:designsystem"))
    implementation(project(":common_resource"))

    implementation(project(":core:domain"))

}