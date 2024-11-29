plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.helponwheels"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.helponwheels"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Firebase SDK and BOM
    implementation(platform("com.google.firebase:firebase-bom:33.3.0")) // Firebase BOM
    implementation("com.google.firebase:firebase-database") // Firebase Database - using BOM to handle versioning
    implementation("com.google.android.gms:play-services-maps:19.0.0")
    implementation ("androidx.cardview:cardview:1.0.0")

    implementation(libs.play.services.location)
    implementation(libs.firebase.auth)
    implementation(libs.cardview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
