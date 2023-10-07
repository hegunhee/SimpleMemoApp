plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    compileOnly(libs.androidGradlePlugin)
    compileOnly(libs.kotlinGradlePlugin)
    compileOnly(libs.hiltGradlePlugin)
}

gradlePlugin {
    plugins {
        register("Android") {
            id = "hegunhee.android"
            implementationClass = "com.hegunhee.build_logic.plugin.AndroidPlugin"
        }
        register("Hilt") {
            id = "hegunhee.hilt"
            implementationClass = "com.hegunhee.build_logic.plugin.HiltPlugin"
        }
        register("AndroidViewFeature") {
            id = "hegunhee.view.feature"
            implementationClass = "com.hegunhee.build_logic.plugin.AndroidViewFeaturePlugin"
        }
    }
}