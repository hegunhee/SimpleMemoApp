package com.hegunhee.build_logic.setup

import org.gradle.api.Project

fun Project.setupViewDataBinding() {
    android {
        viewBinding {
            enable = true
        }
        dataBinding {
            enable = true
        }
    }
}