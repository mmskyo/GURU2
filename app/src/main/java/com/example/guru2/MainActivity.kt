package com.example.guru2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navigationView: BottomNavigationView
    private var currentFragment: Fragment? = null  // 현재 프래그먼트 저장
    private var isTransactionRunning = false // 트랜잭션 중복 실행 방지

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationView = findViewById(R.id.navigationView)

        // savedInstanceState가 null이면 초기 프래그먼트 추가
        if (savedInstanceState == null) {
            replaceFragment(LoginFragment(), showNav = false)
        }

        navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.workout_timer -> {
                    replaceFragment(TimerFragment(), showNav = true)
                    true
                }
                R.id.map -> {
                    replaceFragment(MapFragment(), showNav = true)
                    true
                }
                R.id.my_page -> {
                    replaceFragment(MyPageFragment(), showNav = true)
                    true
                }
                else -> false
            }
        }
    }

    // 중복 실행 방지 + UI 스레드 보장 + 오류 방지
    fun replaceFragment(fragment: Fragment, showNav: Boolean) {
        if (isTransactionRunning) return
        if (currentFragment != null && currentFragment!!::class == fragment::class) return

        isTransactionRunning = true

        runOnUiThread {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, fragment)
            transaction.setReorderingAllowed(false) // 애니메이션 제거
            transaction.commitAllowingStateLoss() // 트랜잭션 강제 실행
            transaction.runOnCommit { isTransactionRunning = false } // 완료 후 플래그 해제
        }

        currentFragment = fragment
        navigationView.visibility = if (showNav) View.VISIBLE else View.GONE
    }
}
