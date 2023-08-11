@file:Suppress("UnstableApiUsage")
plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
        consumerProguardFiles(AppConfig.proguardConsumerRules)
    }

    buildTypes {
        getByName(AppConfig.release) {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(AppConfig.proguardOptimize),
                AppConfig.proguardRules
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding {
        android.buildFeatures.dataBinding = true
    }
}

dependencies {
    implementation(AppDependencies.coreLibraries)
    implementation(AppDependencies.uiLibraries)
    implementation(AppDependencies.lifecycleLibraries)
    kapt(AppDependencies.lifecycleCompiler)
    implementation(AppDependencies.glide)
    annotationProcessor(AppDependencies.glideCompiler)
}