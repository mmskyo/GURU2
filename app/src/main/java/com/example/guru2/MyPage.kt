package com.example.guru2

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text

class MyPage : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var editName : EditText
    lateinit var editPW : EditText
    lateinit var nameBtn : Button
    lateinit var pwBtn : Button
    lateinit var logOut : TextView
    lateinit var name : String
    lateinit var pw : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        editName = findViewById(R.id.editName)
        editPW = findViewById(R.id.editPW)
        nameBtn = findViewById(R.id.nameBtn)
        pwBtn = findViewById(R.id.pwBtn)
        logOut = findViewById(R.id.logOut)
        
        nameBtn.setOnClickListener { 
            name = editName.text.toString()
            Toast.makeText(this, "이름이 변경되었습니다.", Toast.LENGTH_SHORT).show()
        }
        
        pwBtn.setOnClickListener { 
            pw = editPW.text.toString()
            Toast.makeText(this, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show()
        }

        // 운동 종목 수정 스피너
        val spinner: Spinner = findViewById(R.id.editCategory)
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