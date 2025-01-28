package com.example.guru2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class MyPageFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var editName: EditText
    private lateinit var editPW: EditText
    private lateinit var nameBtn: Button
    private lateinit var pwBtn: Button
    private lateinit var logOut: TextView
    private lateinit var name: String
    private lateinit var pw: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragment의 레이아웃을 inflate
        val view = inflater.inflate(R.layout.fragment_my_page, container, false)

        // View 초기화
        editName = view.findViewById(R.id.editName)
        editPW = view.findViewById(R.id.editPW)
        nameBtn = view.findViewById(R.id.nameBtn)
        pwBtn = view.findViewById(R.id.pwBtn)
        logOut = view.findViewById(R.id.logOut)

        // 버튼 클릭 리스너 설정
        nameBtn.setOnClickListener {
            name = editName.text.toString()
            Toast.makeText(requireContext(), "이름이 변경되었습니다.", Toast.LENGTH_SHORT).show()
        }

        pwBtn.setOnClickListener {
            pw = editPW.text.toString()
            Toast.makeText(requireContext(), "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show()
        }

        // 스피너 설정
        val spinner: Spinner = view.findViewById(R.id.editCategory)
        spinner.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.categoryArray,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        return view
    }

    // 스피너에서 항목 선택 시 호출
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedItem = parent?.getItemAtPosition(position).toString()
        Toast.makeText(requireContext(), "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
    }

    // 스피너에서 아무것도 선택되지 않았을 때 호출
    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(requireContext(), "No item selected", Toast.LENGTH_SHORT).show()
    }
}
