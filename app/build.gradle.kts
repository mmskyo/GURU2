plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}
// java.util.properties
//val properties = Properties().apply {
//    load(FileInputStream(rootProject.file("local.properties")))
//}
//val kakaoMapKey = properties.getProperty("KAKAO_MAP_KEY") ?: "d8303786644f79b4b0ae0a133c4a67e2"
android {
    namespace = "com.example.guru2"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.guru2"
        minSdk = 35
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        ndk {
//            abiFilters.addAll(listOf("armeabi-v7a", "arm64-v8a"))
//        }
//        buildConfigField("String", "KAKAO_MAP_KEY", properties.getProperty("KAKAO_MAP_KEY"))
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

    // Configure only for each module that uses Java 8
    // language features (either in its source code or
    // through dependencies).
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
    // For Kotlin projects
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.preference)
    implementation ("com.kakao.maps.open:android:2.12.8") // 카카오 맵
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}