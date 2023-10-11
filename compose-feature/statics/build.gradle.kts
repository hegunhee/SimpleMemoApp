plugins {
    id("hegunhee.compose")
}

android {
    namespace = "com.hegunhee.compose_feature.statics"
}

dependencies {

    implementation(project(":core:designsystem"))
    implementation(project(":common_resource"))
}