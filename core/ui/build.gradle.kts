plugins {
    id("hegunhee.compose")
}

android {
    namespace = "com.hegunhee.newsimplememoapp.core.ui"
}

dependencies {

    implementation(project(":core:designsystem"))
    implementation(project(":domain"))
}