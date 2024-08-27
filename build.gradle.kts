buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:7.4.2")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.4.2")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.44.2")
//        classpath ("org.jetbrains.kotlin.plugin.serialization:1.8.10")/

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
plugins {
    id ("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
}

task("clean",Delete::class) {
    delete = setOf(rootProject.buildDir)
}