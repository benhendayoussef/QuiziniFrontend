plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("io.freefair.lombok") version "6.6.1" // Add the Lombok plugin


}

android {
    namespace = "com.SynClick.quiziniapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.SynClick.quiziniapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.paging.common.jvm)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation (libs.androidx.constraintlayout.compose)
    implementation (libs.accompanist.systemuicontroller)
    implementation ("com.google.accompanist:accompanist-pager:0.12.0")
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation ("io.coil-kt:coil-compose:2.1.0")
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.23")
    implementation ("com.google.code.gson:gson:2.8.9")
    implementation ("org.jsoup:jsoup:1.14.3")
    implementation ("com.google.accompanist:accompanist-systemuicontroller:<latest_version>")
    implementation ("org.projectlombok:lombok:1.18.28") // Add the Lombok dependency
    annotationProcessor ("org.projectlombok:lombok:1.18.28") // Add the annotation processor
    testImplementation ("org.projectlombok:lombok:1.18.28")
    testAnnotationProcessor ("org.projectlombok:lombok:1.18.28")
}