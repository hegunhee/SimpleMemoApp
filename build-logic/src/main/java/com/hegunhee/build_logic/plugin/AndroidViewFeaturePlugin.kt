package com.hegunhee.build_logic.plugin

import com.hegunhee.build_logic.setup.setupViewDataBinding
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidViewFeaturePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("hegunhee.android")
                apply("hegunhee.hilt")
            }
            setupViewDataBinding()

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {


                add("implementation",libs.findBundle("ui").get())

                add("implementation",libs.findLibrary("fragment-ktx").get())
                add("implementation",libs.findBundle("navigation").get())

                add("testImplementation",libs.findLibrary("junit-junit").get())
                add("androidTestImplementation",libs.findLibrary("ext-junit").get())
                add("androidTestImplementation",libs.findLibrary("espresso-core").get())
                add("testImplementation",libs.findLibrary("mockito-inline").get())

                add("implementation",libs.findBundle("lifecycle").get())

                add("implementation",libs.findBundle("coroutine").get())
                add("implementation",libs.findLibrary("coroutine-test").get())
            }
        }
    }
}