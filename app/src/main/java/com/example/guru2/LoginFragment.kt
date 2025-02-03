package com.example.guru2

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.util.regex.Pattern

class LoginFragment : Fragment() {
    lateinit var logId : EditText
    lateinit var logPW : EditText
    lateinit var loginBtn : Button
    lateinit var signBtn : Button
    lateinit var id : String
    lateinit var pw : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        logId = view.findViewById(R.id.logId)
        logPW = view.findViewById(R.id.logPW)
        loginBtn = view.findViewById(R.id.loginBtn)
        signBtn = view.findViewById(R.id.signBtn)

        loginBtn.setOnClickListener {
            val myHelper = MyDBHelper.getInstance(requireContext())
            val db = myHelper.readableDatabase

            val id = logId.text.toString().trim()
            val pw = logPW.text.toString().trim()

            // 입력값 검증
            if (!isValidId(id) || !isValidPassword(pw)) {
                Toast.makeText(requireContext(), "아이디 또는 비밀번호 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 데이터베이스 조회
            val cursor: Cursor = db.rawQuery(
                "SELECT * FROM memberTBL WHERE mId=? AND mPW=?",
                arrayOf(id, pw)
            )

            if (cursor.moveToFirst()) {
                Toast.makeText(requireContext(), "로그인 성공!", Toast.LENGTH_SHORT).show()

                // 네비게이션 바가 있는 화면으로 이동
                val mainActivity = activity as? MainActivity
                mainActivity?.replaceFragment(Fragment(), true)
            } else {
                Toast.makeText(requireContext(), "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }

            cursor.close()
            db.close()
        }

        signBtn.setOnClickListener {
            // 회원가입 화면으로 이동
            val mainActivity = activity as? MainActivity
            mainActivity?.replaceFragment(SignUp(), true)
        }

        return view
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