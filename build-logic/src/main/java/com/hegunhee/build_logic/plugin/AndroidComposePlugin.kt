package com.hegunhee.build_logic.plugin

import com.hegunhee.build_logic.setup.setupAndroid
import com.hegunhee.build_logic.setup.setupAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidComposePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("hegunhee.android")
                apply("hegunhee.hilt")
            }
            setupAndroidCompose()

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("implementation",libs.findLibrary("core-ktx").get())
                add("implementation",platform(libs.findLibrary("kotlin-bom").get()))
                add("implementation",libs.findLibrary("lifecycle-runtime").get())
                add("implementation",platform(libs.findLibrary("compose-bom").get()))
                add("implementation",libs.findBundle("compose").get())
                add("implementation",libs.findLibrary("navigation-compose").get())
                add("implementation",libs.findLibrary("hilt-viewmodel").get())
                add("implementation",libs.findLibrary("junit-junit").get())
                add("androidTestImplementation",libs.findLibrary("ext-junit").get())
                add("androidTestImplementation",libs.findLibrary("espresso-core").get())

                add("androidTestImplementation",platform(libs.findLibrary("compose-test-bom").get()))
                add("androidTestImplementation",libs.findLibrary("compose-ui-test-junit").get())
                add("debugImplementation",libs.findLibrary("compose-ui-tooling").get())
                add("debugImplementation",libs.findLibrary("compose-ui-test-manifest").get())
            }
        }
    }
}