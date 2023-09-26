import com.rickandmorty.buildsrc.Config
import com.rickandmorty.buildsrc.Libs

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = Config.applicationId
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.code
        versionName = Config.name

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    detektPlugins("ru.kode:detekt-rules-compose:1.3.0")
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":usecases"))

    implementation(Libs.Glide.glide)
    implementation(Libs.AndroidX.Navigation.fragmentKtx)
    implementation(Libs.AndroidX.constraintLayout)
    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.Hilt.android)
    implementation(Libs.Hilt.navigationCompose)
    implementation(Libs.Retrofit.retrofit)
    implementation(Libs.Retrofit.converterGson)
    implementation(Libs.JavaX.inject)
    implementation(Libs.Arrow.coredata)
    implementation(Libs.OkHttp3.loginInterceptor)
    implementation(Libs.AndroidX.Activity.compose)
    implementation(Libs.AndroidX.Compose.Material3.material3)
    implementation(Libs.AndroidX.Compose.UI.ui)
    implementation(Libs.AndroidX.Compose.UI.toolingPreview)
    implementation(Libs.AndroidX.Navigation.compose)
    implementation(Libs.Coil.compose)
    implementation(Libs.AndroidX.Room.runtime)
    implementation(Libs.AndroidX.Room.ktx)

    ksp(Libs.AndroidX.Room.compiler)
    ksp(Libs.Hilt.compiler)
}