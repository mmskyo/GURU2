package com.example.guru2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.util.regex.Pattern

class SignUpFragment : Fragment(), AdapterView.OnItemSelectedListener {
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

            val id = setId.text.toString().trim()
            val pw = setPW.text.toString().trim()
            val name = setName.text.toString().trim()
            val category = spinner.selectedItem.toString() // 스피너에서 선택한 운동 종목

            // 아이디 및 비밀번호 형식 검증
            if (!isValidId(id)) {
                Toast.makeText(requireContext(), "아이디는 영문자로만 최대 10자이어야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidPassword(pw)) {
                Toast.makeText(requireContext(), "비밀번호는 영문자와 숫자를 포함하여 8~10자이어야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (name.isEmpty()) {
                Toast.makeText(requireContext(), "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 회원가입 데이터 저장
            val insertQuery = "INSERT INTO memberTBL (mId, mPW, mName, mCat) VALUES (?, ?, ?, ?);"
            val stmt = db.compileStatement(insertQuery)
            stmt.bindString(1, id)
            stmt.bindString(2, pw)
            stmt.bindString(3, name)
            stmt.bindString(4, category)

            try {
                stmt.executeInsert() // 실행
                Toast.makeText(requireContext(), "회원가입이 되었습니다.", Toast.LENGTH_SHORT).show()

                // 성공하면 로그인으로 이동 수정!!
                val mainActivity = activity as? MainActivity
                mainActivity?.replaceFragment(LoginFragment(), false)
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


    // 아이디 검증 (영문자로만 최대 10자)
    private fun isValidId(id: String): Boolean {
        val idPattern = "^[a-zA-Z]{1,10}$"
        return Pattern.matches(idPattern, id)
    }

    // 비밀번호 검증 (영문자 + 숫자, 8~10자)
    private fun isValidPassword(pw: String): Boolean {
        val pwPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,10}$"
        return Pattern.matches(pwPattern, pw)
    }
}
