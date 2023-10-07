package com.hegunhee.build_logic.plugin

import com.hegunhee.build_logic.setup.androidApplication
import com.hegunhee.build_logic.setup.setupAndroid
import com.hegunhee.build_logic.setup.setupViewDataBinding
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("hegunhee.hilt")
            }
            androidApplication {
                setupAndroid()
                setupViewDataBinding()

                defaultConfig {
                    applicationId = "com.hegunhee.newsimplememoapp"
                    versionCode = (1)
                    versionName = "1.0"

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
                buildTypes {
                    getByName("release"){
                        isMinifyEnabled = false
                        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                    }
                }
            }
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                add("implementation",libs.findBundle("ui").get())

                add("implementation",libs.findBundle("navigation").get())

                add("testImplementation",libs.findLibrary("junit-junit").get())
                add("androidTestImplementation",libs.findLibrary("ext-junit").get())
                add("androidTestImplementation",libs.findLibrary("espresso-core").get())
                add("testImplementation",libs.findLibrary("mockito-inline").get())

                add("implementation",libs.findBundle("lifecycle").get())

                add("implementation",libs.findBundle("coroutine").get())
                add("implementation",libs.findLibrary("coroutine-test").get())

                add("implementation",libs.findBundle("room").get())
                add("kapt",libs.findLibrary("room-compiler").get())

                add("androidTestImplementation",libs.findLibrary("room-testing").get())
                add("androidTestImplementation",libs.findLibrary("test-ext-junit").get())
                add("androidTestImplementation",libs.findLibrary("mockito-core").get())

            }
        }
    }
}