package com.example.guru2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
lateinit var navigationView : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 초기 프래그먼트
        replaceFragment(LoginFragment(), false)

        val navigationView = findViewById<BottomNavigationView>(R.id.navigationView)

        // 앱 실행 시 로그인 화면으로 이동 (네비게이션 바 숨김)
        replaceFragment(LoginFragment(), false)

        navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.workout_timer -> {
                    replaceFragment(TimerFragment(), true)
                    true
                }
                R.id.map -> {
                    replaceFragment(MapFragment(), true)
                    true
                }
                // 하늘님 파트
//                R.id.record -> {
//                    replaceFragment(RecordsFragment(), true)
//                    true
//                }
                R.id.my_page -> {
                    replaceFragment(MyPageFragment(), true)
                    true
                }
                else -> false
            }
        }
    }

    // Fragment 전환 함수
    fun replaceFragment(fragment : Fragment, showNav : Boolean) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment) // 기존 프래그먼트 컨테이너를 대체
        fragmentTransaction.commit()

        // 네비게이션 바 표시 여부 설정
        if (showNav) {
            navigationView.visibility = View.VISIBLE
        } else {
            navigationView.visibility = View.GONE
        }
    }

}
