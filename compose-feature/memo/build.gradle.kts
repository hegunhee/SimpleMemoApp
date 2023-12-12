plugins {
    id("hegunhee.compose")
}

android {
    namespace = "com.hegunhee.compose_feature.memo"
}

dependencies {

    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
    implementation(project(":common_resource"))
    implementation(project(":core:util"))

    implementation(project(":core:domain"))
}