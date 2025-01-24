package com.example.guru2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Timer
import kotlin.concurrent.timer

class WorkoutMonitor : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var time = 0
    private var timerTask : Timer? = null
    private var savedTime : Int = 0
    lateinit var startButton: Button
    lateinit var endButton: Button
    lateinit var secTextView: TextView
    lateinit var milliTextView: TextView

    // 타이머 시작
    private fun start() {
        // 타이머 시작 시 시작 버튼 비활성화
        startButton.setEnabled(/* enabled = */ false)

        // 타이머
        timerTask = timer(period = 10) {
            time++
            val sec = time / 100
            val milli = time % 100
            runOnUiThread {
                secTextView.text = "$sec"
                milliTextView.text = "$milli"
            }
        }
    }
    // 타이머 종료
    private fun end() {
        // 종료 버튼 비활성화
        endButton.setEnabled(false)

        // 타임 저장
        savedTime = this.time
        Toast.makeText(this, "${savedTime / 100}.${savedTime % 100}", Toast.LENGTH_SHORT).show()

        // 실행 중인 타이머 종료
        timerTask?.cancel()

        // 모든 변수 초기화
        time = 0
        startButton.setEnabled(true) // 시작 버튼 다시 활성화
        secTextView.text = "0"
        milliTextView.text = "00"
    }
    // 메인
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_monitor)

        startButton = findViewById(R.id.startButton)
        endButton = findViewById(R.id.endButton)
        secTextView = findViewById(R.id.secTextView)
        milliTextView = findViewById(R.id.milliTextView)

        // 카테고리 스피너
        val spinner: Spinner = findViewById(R.id.categorySpinner)
        spinner.onItemSelectedListener = this
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter.createFromResource(
            this,
            R.array.categoryArray,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // 드롭다운 레이아웃
            // Apply the adapter to the spinner.
            spinner.adapter = adapter
        }

        // 시작 버튼 리스너
        startButton.setOnClickListener {
            start()
        }
        // 종료 버튼 리스너
        endButton.setOnClickListener {
            var intent = Intent(this, MyPage::class.java) // 기록작성페이지로 수정해야함
            end()
            intent.putExtra("시간", savedTime) // 시간 데이터 추출
            startActivity(intent)
        }
    }

    // 스피너에서 항목 선택 시 호출
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedItem = parent?.getItemAtPosition(position).toString()
        Toast.makeText(this, "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
    }

    // 스피너에서 아무것도 선택되지 않았을 때 호출
    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(this, "No item selected", Toast.LENGTH_SHORT).show()
    }

}