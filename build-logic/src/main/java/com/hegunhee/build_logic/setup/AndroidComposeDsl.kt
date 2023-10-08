package com.hegunhee.build_logic.setup

import org.gradle.api.Project

fun Project.setupAndroidCompose() {
    androidLibrary {
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = "1.4.0"
        }
    }
}

fun Project.setupAndroidComposeApplication() {
    androidApplication {
        composeOptions {
            kotlinCompilerExtensionVersion = "1.4.0"
        }
        buildFeatures {
            compose = true
        }
    }
}