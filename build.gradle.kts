// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(dependencyNotation = "com.android.tools.build:gradle:4.1.3")
        classpath(dependencyNotation = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.0")
        classpath(dependencyNotation = "com.google.dagger:hilt-android-gradle-plugin:2.35")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}

task("clean") {
    delete(rootProject.buildDir)
}