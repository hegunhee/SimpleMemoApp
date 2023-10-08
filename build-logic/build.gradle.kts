plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
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
        register("AndroidApplication") {
            id = "hegunhee.application"
            implementationClass = "com.hegunhee.build_logic.plugin.AndroidApplicationPlugin"
        }
        register("AndroidCompose") {
            id = "hegunhee.compose"
            implementationClass = "com.hegunhee.build_logic.plugin.AndroidComposePlugin"
        }
        register("AndroidApplicationCompose") {
            id = "hegunhee.application.compose"
            implementationClass = "com.hegunhee.build_logic.plugin.AndroidComposeApplicationPlugin"
        }
    }
}