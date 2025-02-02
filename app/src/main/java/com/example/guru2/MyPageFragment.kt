package com.example.guru2

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class MyPageFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var editName: EditText
    private lateinit var editPW: EditText
    private lateinit var saveBtn: Button
    private lateinit var logOut: TextView
    private lateinit var spinner: Spinner
    private lateinit var myHelper: MyDBHelper
    private lateinit var sqlDB: SQLiteDatabase

    private var userId: String = "" // 현재 로그인한 사용자의 ID
    private var selectedCategory: String = "" // 선택된 운동 카테고리

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_page, container, false)

        myHelper = MyDBHelper.getInstance(requireContext()) // DB 헬퍼 초기화

        // View 초기화
        editName = view.findViewById(R.id.editName)
        editPW = view.findViewById(R.id.editPW)
        saveBtn = view.findViewById(R.id.saveBtn)
        logOut = view.findViewById(R.id.logOut)
        spinner = view.findViewById(R.id.editCategory)

        // 스피너 설정
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.categoryArray,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this

        // 현재 로그인한 유저 정보 가져오기
        userId = getCurrentUserId()
        loadUserInfo(userId)

        // 저장 버튼 리스너
        saveBtn.setOnClickListener {
            saveUserInfo(userId)
        }

        return view
    }

    /**
     *  현재 로그인한 사용자 ID를 가져오는 함수 (구현 필요)
     *  SharedPreferences, 글로벌 변수, 또는 로그인 시 저장한 데이터에서 불러오기
     */
    private fun getCurrentUserId(): String {
        // 예제: SharedPreferences에서 저장된 userId를 가져오기
        val sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", 0)
        return sharedPreferences.getString("userId", "") ?: ""
    }

    /**
     *  데이터베이스에서 사용자 정보 조회 후 EditText 및 Spinner에 설정
     */
    private fun loadUserInfo(userId: String) {
        sqlDB = myHelper.readableDatabase
        val cursor = sqlDB.rawQuery("SELECT mName, mPW, mCat FROM memberTBL WHERE mId=?", arrayOf(userId))

        if (cursor.moveToFirst()) {
            val name = cursor.getString(0)  // mName
            val pw = cursor.getString(1)    // mPW
            selectedCategory = cursor.getString(2) // mCat

            // EditText에 값 설정
            editName.setText(name)
            editPW.setText(pw)

            // 스피너에서 저장된 카테고리를 기본 선택값으로 설정
            val categoryArray = resources.getStringArray(R.array.categoryArray)
            val categoryIndex = categoryArray.indexOf(selectedCategory)
            if (categoryIndex >= 0) {
                spinner.setSelection(categoryIndex)
            }
        }
        cursor.close()
        sqlDB.close()
    }

    /**
     *  사용자가 수정한 정보를 DB에 업데이트
     */
    private fun saveUserInfo(userId: String) {
        val newName = editName.text.toString()
        val newPW = editPW.text.toString()
        val newCategory = selectedCategory

        sqlDB = myHelper.writableDatabase

        val values = ContentValues().apply {
            put("mName", newName)
            put("mPW", newPW)
            put("mCat", newCategory)
        }

        val result = sqlDB.update("memberTBL", values, "mId=?", arrayOf(userId))

        if (result > 0) {
            Toast.makeText(requireContext(), "정보가 저장되었습니다.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "정보 저장 실패", Toast.LENGTH_SHORT).show()
        }

        sqlDB.close()
    }

    // 스피너에서 항목 선택 시 호출
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedCategory = parent?.getItemAtPosition(position).toString()
    }

    // 스피너에서 아무것도 선택되지 않았을 때 호출
    override fun onNothingSelected(parent: AdapterView<*>?) {
        // 기본값 유지
    }
}
