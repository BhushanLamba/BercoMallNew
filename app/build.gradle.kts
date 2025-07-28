plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)

}

android {
    namespace = "it.softbrain.barcomall"
    compileSdk = 35

    defaultConfig {
        applicationId = "it.softbrain.barcomall"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    viewBinding.enable=true
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    //GOOGLE GSON
    implementation(libs.gson)

    //RETROFIT
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    // LIVE DATA
    implementation(libs.androidx.lifecycle.livedata.ktx)
    kapt(libs.androidx.lifecycle.compiler)

    //HILT
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    //GLIDE
    implementation(libs.glide)

    //OKHTTP INTERCEPTOR
    implementation(libs.logging.interceptor)

    //ROOM
    implementation(libs.androidx.room.runtime.android)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    //IMAGE SLIDER
    implementation(libs.imageslideshow)

    //Zoom image
    implementation(libs.zoomage)


}