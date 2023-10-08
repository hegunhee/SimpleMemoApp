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

}