package com.example.guru2

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.Toast

class SignUp : Fragment(), AdapterView.OnItemSelectedListener {
    lateinit var setId : EditText
    lateinit var setPW : EditText
    lateinit var setName : EditText
    lateinit var id : String
    lateinit var pw : String
    lateinit var name : String
    lateinit var signUpBtn : Button

    var selectedCategory: String = "러닝" // 기본값을 '러닝'으로 설정

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        setId = view.findViewById(R.id.setId)
        setPW = view.findViewById(R.id.setPW)
        setName = view.findViewById(R.id.setName)
        signUpBtn = view.findViewById(R.id.signupBtn)

        // 스피너 설정
        val spinner: Spinner = view.findViewById(R.id.setCat)
        spinner.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.categoryArray,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        signUpBtn.setOnClickListener {
            val myHelper = MyDBHelper.getInstance(requireContext())

            val db = myHelper.writableDatabase

            id = setId.text.toString()
            pw = setPW.text.toString()
            name = setName.text.toString()
            val category = spinner.selectedItem.toString() // 스피너에서 선택한 운동 종목

            // 회원가입 데이터 저장
            val insertQuery = "INSERT INTO memberTBL (mId, mPW, mName, mCat) VALUES (?, ?, ?, ?);"
            val stmt = db.compileStatement(insertQuery)
            stmt.bindString(1, id)
            stmt.bindString(2, pw)
            stmt.bindString(3, name)
            stmt.bindString(4, category)

            try {
                stmt.executeInsert() // 실행
                Toast.makeText(requireContext(), "회원가입 완료!", Toast.LENGTH_SHORT).show()

                // 성공하면 로그인으로 이동 수정!!
                val mainActivity = activity as? MainActivity
                mainActivity?.replaceFragment(TimerFragment())
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "회원가입 실패: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                stmt.close()
                db.close()
            }
        }
        return view
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedCategory = parent?.getItemAtPosition(position).toString()
        Toast.makeText(requireContext(), "선택한 운동: $selectedCategory", Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // 선택하지 않으면 기본값 '러닝' 유지
    }
}
