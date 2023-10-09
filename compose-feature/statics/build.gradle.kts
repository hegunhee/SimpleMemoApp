plugins {
    id("hegunhee.compose")
}

android {
    namespace = "com.hegunhee.compose_feature.statics"
}

dependencies {
    implementation(project(":common_resource"))
}