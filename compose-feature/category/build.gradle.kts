plugins {
    id("hegunhee.compose")
}

android {
    namespace = "com.hegunhee.category"
}

dependencies {

    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
    implementation(project(":common_resource"))
    implementation(project(":compose-feature:util"))

    implementation(project(":core:domain"))
}