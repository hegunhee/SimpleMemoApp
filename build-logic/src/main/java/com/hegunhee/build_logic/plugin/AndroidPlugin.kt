package com.hegunhee.build_logic.plugin

import com.hegunhee.build_logic.setup.androidLibrary
import com.hegunhee.build_logic.setup.setupAndroid
import com.hegunhee.build_logic.setup.setupViewDataBinding
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target){
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            androidLibrary {
                setupAndroid()
                setupViewDataBinding()

                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }
                buildTypes {
                    getByName("release"){
                        isMinifyEnabled = false
                        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                    }
                }
            }
        }
    }
}