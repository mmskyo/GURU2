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

            var tableCursor: Cursor? = null
            var loginCursor: Cursor? = null

            try {
                tableCursor = db.rawQuery(
                    "SELECT name FROM sqlite_master WHERE type='table' AND name='memberTBL';",
                    null
                )

                if (tableCursor!!.count == 0) {
                    Toast.makeText(requireContext(), "회원 데이터가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    loginCursor = db.rawQuery(
                        "SELECT * FROM memberTBL WHERE mId=? AND mPW=?",
                        arrayOf(id, pw)
                    )

                    if (loginCursor!!.moveToFirst()) {
                        Toast.makeText(requireContext(), "로그인 성공!", Toast.LENGTH_SHORT).show()
                        // 타이머 화면으로 이동
                        val mainActivity = activity as? MainActivity
                        mainActivity?.replaceFragment(TimerFragment(), true)
                    } else {
                        Toast.makeText(requireContext(), "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "데이터베이스 오류: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                tableCursor?.close()
                loginCursor?.close()
                db.close()
            }
        }

        signBtn.setOnClickListener {
            // 회원가입 화면으로 이동
            val mainActivity = activity as? MainActivity
            mainActivity?.replaceFragment(SignUpFragment(), false)
        }

        return view
    }

}