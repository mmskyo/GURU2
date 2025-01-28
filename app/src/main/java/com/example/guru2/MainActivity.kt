package com.example.guru2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 초기 프래그먼트 설정
        replaceFragment(TimerFragment())

        val navigationView = findViewById<BottomNavigationView>(R.id.navigationView)

        navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.workout_timer -> {
                    replaceFragment(TimerFragment())
                    true
                }
//                R.id.map -> {
//                    replaceFragment(MapFragment2())
//                    true
//                }
//                R.id.record -> {
//                    replaceFragment(RecordsFragment())
//                    true
//                }
                R.id.my_page -> {
                    replaceFragment(MyPageFragment())
                    true
                }
                else -> false
            }
        }
    }

    // Fragment 전환 함수
    fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment) // 기존 프래그먼트 컨테이너를 대체
        fragmentTransaction.commit()
    }

}
