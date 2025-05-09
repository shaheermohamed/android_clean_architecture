plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android") // ✅ not kotlin-android, must use full id
    id("dagger.hilt.android.plugin")
    id ("kotlin-kapt")
    id ("org.jetbrains.kotlin.plugin.compose")  // <-- Add this line
    id("com.google.gms.google-services")
}

android {
    namespace = "com.shahe.basiclearning"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.shahe.basiclearning"
        minSdk = 24
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion =  "1.5.10"  // Use the latest compatible version
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation ("androidx.core:core-ktx:1.16.0")
    implementation ("androidx.appcompat:appcompat:1.7.0")
    implementation ("com.google.android.material:material:1.12.0")
    implementation ("androidx.compose.ui:ui:1.8.0")
    implementation ("androidx.compose.material:material:1.8.0")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.8.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation ("androidx.activity:activity-compose:1.10.1")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.2.1")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.8.0")
    debugImplementation ("androidx.compose.ui:ui-tooling:1.8.0")

    // Compose dependencies
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    implementation ("androidx.navigation:navigation-compose:2.8.9")
    implementation ("com.google.accompanist:accompanist-flowlayout:0.17.0")

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1")

    // Coroutine Lifecycle Scopes
    implementation  ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation  ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")

    //Dagger - Hilt
    implementation ("com.google.dagger:hilt-android:2.55")
    kapt("com.google.dagger:hilt-android-compiler:2.55")
    kapt ("androidx.hilt:hilt-compiler:1.2.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    implementation("io.coil-kt:coil-compose:2.4.0")

    implementation("androidx.room:room-runtime:2.7.1")
    kapt("androidx.room:room-compiler:2.7.1") // KAPT (Java/Kotlin)

    // Kotlin Extensions + Coroutines support
    implementation("androidx.room:room-ktx:2.6.1")

    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("com.google.accompanist:accompanist-permissions:0.35.0-alpha")

    implementation("com.google.firebase:firebase-auth-ktx:22.3.1") // ✅ Required
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("com.google.firebase:firebase-messaging:24.1.1")
}