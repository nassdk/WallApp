plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(apiLevel = 30)
    buildToolsVersion(version = "30.0.3")

    defaultConfig {
        applicationId(applicationId = "com.nassdk.wallapp")
        minSdkVersion(minSdkVersion = 21)
        targetSdkVersion(targetSdkVersion = 30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                argument(key = "room.schemaLocation", value = "${projectDir}/schemas")
                argument(key = "room.incremental", value = "true")
                argument(key = "room.expandProjection", value = "true")
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(name = "proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        exclude(pattern = "META-INF/gradle/incremental.annotation.processors")
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {

    implementation(dependencyNotation = "org.jetbrains.kotlin:kotlin-stdlib:1.5.0")

    implementation(dependencyNotation = "androidx.core:core-ktx:1.5.0")
    implementation(dependencyNotation = "androidx.appcompat:appcompat:1.3.0")
    implementation(dependencyNotation = "androidx.constraintlayout:constraintlayout:2.0.4")

    implementation(dependencyNotation = "com.google.android.material:material:1.3.0")

    implementation(dependencyNotation = "com.arkivanov.mvikotlin:mvikotlin:2.0.1")
    implementation(dependencyNotation = "com.arkivanov.mvikotlin:mvikotlin-main:2.0.1")
    implementation(dependencyNotation = "com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:2.0.1")

    implementation(dependencyNotation = "androidx.room:room-runtime:2.3.0")
    kapt(dependencyNotation = "androidx.room:room-compiler:2.3.0")

    implementation(dependencyNotation = "androidx.room:room-ktx:2.3.0")

    implementation(dependencyNotation = "com.google.dagger:hilt-android:2.35")
    kapt(dependencyNotation = "com.google.dagger:hilt-compiler:2.35")

    implementation(dependencyNotation = "com.facebook.shimmer:shimmer:0.5.0")

    implementation(dependencyNotation = "com.google.code.gson:gson:2.8.6")

    implementation(dependencyNotation = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")

    implementation(dependencyNotation = "com.squareup.okhttp3:okhttp:4.9.0")
    implementation(dependencyNotation = "com.squareup.okhttp3:logging-interceptor:4.9.0")

    implementation(dependencyNotation = "com.squareup.retrofit2:retrofit:2.9.0")
    implementation(dependencyNotation = "com.squareup.retrofit2:converter-gson:2.9.0")

    implementation(dependencyNotation = "io.coil-kt:coil:1.1.1")

    implementation(dependencyNotation = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3")
}