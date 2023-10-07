package com.rickandmorty.buildsrc

object Config {
    const val applicationId = "com.rickandmorty"
    const val minSdk = 26
    const val targetSdk = 34
    const val compileSdk = 34
    const val name = "0.0.1"
    const val code = 1
}

object Gradle {
    private const val gradleVersion = "8.1.1"
    private const val kotlinVersion = "1.7.21"
    const val androidPlugin = "com.android.tools.build:gradle:${gradleVersion}"
    const val versionsPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}"
}

object Libs {
    object Retrofit {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val converterGson = "com.squareup.retrofit2:converter-gson:$version"
    }

    object OkHttp3 {
        private const val version = "4.11.0"
        const val loginInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
        const val mockWebServer = "com.squareup.okhttp3:mockwebserver:$version"
    }

    object Arrow {
        private const val version = "1.1.5"
        const val core = "io.arrow-kt:arrow-core:$version"
        const val coredata = "io.arrow-kt:arrow-core-data:0.10.3"
    }

    object Glide {
        private const val version = "4.16.0"
        const val glide = "com.github.bumptech.glide:glide:$version"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
    }

    object Coil {
        private const val version = "2.2.0"
        const val compose = "io.coil-kt:coil-compose:$version"
        const val gif = "io.coil-kt:coil-gif:$version"
    }

    object JavaX {
        const val inject = "javax.inject:javax.inject:1"
    }

    object Hilt {
        const val version = "2.47"
        const val android = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-compiler:$version"
        const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val test = "com.google.dagger:hilt-android-testing:$version"
        const val navigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0"
    }

    object Kotlin {
        const val version = "1.8.10"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"

        object Coroutines {
            private const val version = "1.7.1"

            //            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.10.1"
        const val appCompat = "androidx.appcompat:appcompat:1.6.1"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.3.0"
        const val material = "com.google.android.material:material:1.9.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"

        object Activity {
            private const val version = "1.7.2"
            const val ktx = "androidx.activity:activity-ktx:$version"
            const val compose = "androidx.activity:activity-compose:$version"
        }

        object Lifecycle {
            private const val version = "2.6.1"
            const val viewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val viewmodelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
        }

        object Navigation {
            const val version = "2.7.3"
            const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:$version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"

            //            const val gradlePlugin =
//                "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
            const val compose = "androidx.navigation:navigation-compose:$version"
        }

        object Room {
            private const val version = "2.5.2"
            const val runtime = "androidx.room:room-runtime:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val compiler = "androidx.room:room-compiler:$version"
        }

        object Test {
            private const val version = "1.5.0"
            const val runner = "androidx.test:runner:$version"
            const val rules = "androidx.test:rules:$version"

            object Ext {
                private const val version = "1.1.5"
                const val junit = "androidx.test.ext:junit-ktx:$version"
            }

            object Espresso {
                private const val version = "3.5.1"
                const val contrib = "androidx.test.espresso:espresso-contrib:$version"
            }
        }

        object Compose {
            object UI {
                private const val version = "1.5.0"
                const val ui = "androidx.compose.ui:ui:$version"
                const val tooling = "androidx.compose.ui:ui-tooling:$version"
                const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:$version"
            }

            object Material3 {
                const val material3 = "androidx.compose.material3:material3:1.2.0-alpha05"
                const val materialIconsExtended =
                    "androidx.compose.material:material-icons-extended-android:1.5.0"
            }
        }
    }

    object JUnit {
        private const val version = "4.13.2"
        const val junit = "junit:junit:$version"
    }

    object Mockito {
        const val kotlin = "org.mockito.kotlin:mockito-kotlin:5.0.0"
        const val inline = "org.mockito:mockito-inline:5.2.0"
    }
}