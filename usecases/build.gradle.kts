import com.alvaroquintana.rickandmorty.buildsrc.Libs

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(Libs.Kotlin.Coroutines.core)
    implementation(Libs.Retrofit.retrofit)
    implementation(Libs.JavaX.inject)
    implementation(Libs.Arrow.coredata)
}