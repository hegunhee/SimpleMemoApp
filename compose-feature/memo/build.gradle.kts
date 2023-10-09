plugins {
    id("hegunhee.compose")
}

android {
    namespace = "com.hegunhee.compose_feature.memo"
}

dependencies {
    implementation(project(":common_resource"))
}