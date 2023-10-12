package com.hegunhee.build_logic.plugin

import com.hegunhee.build_logic.setup.androidApplication
import com.hegunhee.build_logic.setup.setupAndroid
import com.hegunhee.build_logic.setup.setupAndroidComposeApplication
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidComposeApplicationPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("hegunhee.hilt")
            }
            androidApplication {
                setupAndroid()
                setupAndroidComposeApplication()


                defaultConfig {
                    applicationId = "com.hegunhee.compose_app"
                    versionCode = (1)
                    versionName = "1.0"

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    vectorDrawables {
                        useSupportLibrary = true
                    }
                    compileSdkPreview = "UpsideDownCake"
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
                add("implementation",libs.findLibrary("core-ktx").get())
                add("implementation",platform(libs.findLibrary("kotlin-bom").get()))
                add("implementation",platform(libs.findLibrary("compose-bom").get()))
                add("implementation",libs.findBundle("compose-ui").get())
                add("implementation",libs.findLibrary("navigation-compose").get())
                add("implementation",libs.findLibrary("hilt-viewmodel").get())
                add("implementation",libs.findLibrary("junit-junit").get())
                add("androidTestImplementation",libs.findLibrary("ext-junit").get())
                add("androidTestImplementation",libs.findLibrary("espresso-core").get())

                add("androidTestImplementation",platform(libs.findLibrary("compose-test-bom").get()))
                add("androidTestImplementation",libs.findLibrary("compose-ui-test-junit").get())
                add("debugImplementation",libs.findLibrary("compose-ui-tooling").get())
                add("debugImplementation",libs.findLibrary("compose-ui-test-manifest").get())

                add("implementation",libs.findBundle("lifecycle").get())
                add("implementation",libs.findLibrary("lifecycle-compose").get())

                add("implementation",libs.findBundle("coroutine").get())
                add("implementation",libs.findLibrary("coroutine-test").get())

                add("implementation",libs.findBundle("room").get())
                add("kapt",libs.findLibrary("room-compiler").get())

                add("androidTestImplementation",libs.findLibrary("room-testing").get())
                add("androidTestImplementation",libs.findLibrary("mockito-core").get())

            }
        }
    }
}