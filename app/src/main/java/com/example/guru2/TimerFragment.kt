package com.example.guru2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.util.Timer
import kotlin.concurrent.timer

class TimerFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var time = 0
    private var timerTask: Timer? = null
    private var no: Int = 0
    private lateinit var startButton: Button
    private lateinit var endButton: Button
    private lateinit var minTextView: TextView
    private lateinit var secTextView: TextView
    private lateinit var locationTextView: TextView
    private lateinit var myHelper: MyDBHelper
    private lateinit var sqlDB: SQLiteDatabase
    private lateinit var strTime: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_timer, container, false)

        myHelper = MyDBHelper.getInstance(requireContext())

        startButton = view.findViewById(R.id.startButton)
        endButton = view.findViewById(R.id.endButton)
        minTextView = view.findViewById(R.id.minTextView)
        secTextView = view.findViewById(R.id.secTextView)
        locationTextView = view.findViewById(R.id.locationTextView)

        // 스피너 설정
        val spinner: Spinner = view.findViewById(R.id.categorySpinner)
        spinner.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.categoryArray,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        // 종료 버튼 비활성화
        endButton.isEnabled = false

        // 위치 찾기 버튼 리스너
        locationTextView.setOnClickListener {
            // 사용자가 입력하는걸로 대신
            // xml에서 editText로 바꿔야함
        }

        // 시작 버튼 리스너
        startButton.setOnClickListener {
            start()
        }
        // 종료 버튼 리스너
        endButton.setOnClickListener {
            no++

            strTime = String.format("%02d:%02d", time / 60, time % 60)
            end()
            sqlDB = myHelper.writableDatabase
            sqlDB.execSQL( //${locationTextView.text}
                "INSERT INTO timeTBL VALUES ('${getUserId()}', '$no', '$strTime', 'location0', '${spinner.selectedItem}');"
            )
            sqlDB.close()

            // 기록 프래그먼트로 전환 수정해야함
            val mainActivity = activity as? MainActivity
            mainActivity?.replaceFragment(MyPageFragment(), true)
        }

        return view
    }

    private fun start() {
        // 타이머 시작 시 종료 버튼 제외한 모든 뷰 비활성화
        setClickEnabled(false)

        // 타이머 주기를 1초로 설정
        timerTask = timer(period = 1000) {
            time++
            val min = time / 60 // 1초마다 증가하므로, 60초당 1분
            val sec = time % 60 // 60초가 넘어가면 초가 0으로 초기화

            // 프래그먼트가 액티비티에 연결된 상태에서만 실행
            if (isAdded && activity != null) {
                activity?.runOnUiThread {
                    minTextView.text = String.format("%02d", min) // 두자리 표현
                    secTextView.text = String.format("%02d", sec)
                }
            } else {
                // 프래그먼트가 분리된 경우 타이머 종료
                timerTask?.cancel()
            }
        }
    }

    private fun end() {
        // 타이머 종료 시 모든 뷰 다시 활성화
        setClickEnabled(true)

        // 타이머 종료
        timerTask?.cancel()

        // 초기화
        time = 0
        startButton.isEnabled = true
        minTextView.text = "00"
        secTextView.text = "00"
    }

    // 뷰 활성화/비활성화 메서드
    private fun setClickEnabled(enabled: Boolean) {
        startButton.isEnabled = enabled
        endButton.isEnabled = !enabled // 종료 버튼은 반대로 활성화
        // 비활성화할 다른 버튼들 추가
        locationTextView.isEnabled = enabled
        // spinner.isEnabled = enabled
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedItem = parent?.getItemAtPosition(position).toString()
        Toast.makeText(requireContext(), "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(requireContext(), "No item selected", Toast.LENGTH_SHORT).show()
    }

    // 아이디
    private fun getUserId(): String? {
        val sharedPref = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        return sharedPref.getString("userId", null) // 저장된 userId 가져오기
    }

}
