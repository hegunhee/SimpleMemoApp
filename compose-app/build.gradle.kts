plugins {
    id("hegunhee.application.compose")
}

android {
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":core:designsystem"))
    implementation(project(":compose-feature:memo"))
    implementation(project(":compose-feature:statics"))
    implementation(project(":common_resource"))

    implementation(project(":domain"))
    implementation(project(":data"))
}