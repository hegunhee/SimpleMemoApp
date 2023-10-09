plugins {
    id("hegunhee.compose")
}

android {
    namespace = "com.hegunhee.compose_feature"
}
dependencies {
    implementation(project(":common_resource"))
}