// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
}
buildscript {

    repositories {
        // Make sure that you have the following two repositories
        google()  // Google's Maven repository
        mavenCentral()  // Maven Central repository
    }

    dependencies {

        // Add the Maven coordinates and latest version of the plugin
        classpath ("com.google.firebase:firebase-database:20.3.0")
        classpath("com.google.gms:google-services:4.4.0")

    }
}

