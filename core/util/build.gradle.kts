plugins {
    id ("hegunhee.android")
}

android {
    namespace = "com.hegunhee.newsimplememoapp.util"
}


dependencies {

    implementation(project(":core:domain"))
}