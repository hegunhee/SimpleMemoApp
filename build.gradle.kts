buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:4.2.1")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.4.2")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.40.1")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

task("clean",Delete::class) {
    delete = setOf(rootProject.buildDir)
}