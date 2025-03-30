package com.taichi.prompts.android.common

import com.taichi.prompts.android.R
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Industry(
    val name: String,
    val occupations: List<String>
)

    class OccupationPickerDialog(
        context: Context,
        private val onSelected: (industry: String, occupation: String) -> Unit
    ) : BottomSheetDialog(context, R.style.BottomSheetDialog) { // 使用 BottomSheetDialog

        private lateinit var industries: List<Industry>
        private var currentOccupations: List<String> = emptyList()

        private lateinit var npIndustry: NumberPicker
        private lateinit var npOccupation: NumberPicker
        private lateinit var btnConfirm: Button

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.diaglog_occupation_picker)

            // 设置对话框行为
            behavior.apply {
                isFitToContents = true
                isHideable = true
                skipCollapsed = true
            }

            // 加载数据
            loadIndustryData()

            npIndustry = requireNotNull(findViewById(R.id.npIndustry)) {
                "NumberPicker npIndustry not found in layout"
            }
            npOccupation = requireNotNull(findViewById(R.id.npOccupation)) {
                "NumberPicker npOccupation not found in layout"
            }
            btnConfirm = requireNotNull(findViewById(R.id.btnConfirm)) {
                "Button btnConfirm not found in layout"
            }

            // 初始化选择器
            npIndustry!!.apply {
                wrapSelectorWheel = false
                minValue = 0
                maxValue = industries.size - 1
                displayedValues = industries.map { it.name }.toTypedArray()
                setOnValueChangedListener { _, _, newPos ->
                    updateOccupationPicker(industries[newPos].occupations, npOccupation)
                }
            }

            updateOccupationPicker(industries.first().occupations, npOccupation)

            btnConfirm!!.setOnClickListener {
                val industry = industries[npIndustry?.value ?: 0]
                val occupation = currentOccupations.getOrNull(npOccupation?.value ?: 0) ?: ""
                if (occupation.isNotEmpty()) {
                    onSelected(industry.name, occupation)
                    dismiss()
                } else {
                    Toast.makeText(context, "请选择有效职业", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun loadIndustryData() {
        try {
            val json = context.assets.open("industries.json").bufferedReader().use { it.readText() }
            industries = Gson().fromJson(json, object : TypeToken<List<Industry>>() {}.type)
        } catch (e: Exception) {
            Log.e("OccupationPicker", "数据加载失败", e)
            industries = emptyList()
        }
    }

    private fun updateOccupationPicker(occupations: List<String>, picker: NumberPicker) {
        currentOccupations = occupations
        picker.apply {
            wrapSelectorWheel = false
            minValue = 0
            maxValue = occupations.size - 1
            displayedValues = occupations.toTypedArray()
            value = 0
        }
    }
}