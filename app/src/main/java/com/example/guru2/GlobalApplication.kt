package com.example.guru2

import android.app.Application
import com.kakao.vectormap.KakaoMapSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        // Kakao SDK 초기화! 메인액티비티말고 여기서->전역적 설정
        KakaoMapSdk.init(this, BuildConfig.KAKAO_MAP_KEY)
    }
}