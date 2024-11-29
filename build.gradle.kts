// Project-level build.gradle.kts (Kotlin DSL version)
buildscript {
    repositories {
        google()  // Ensure Google repository is included
        mavenCentral()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.3.15") // Google services classpath
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}
