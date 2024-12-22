package com.taichi.prompts.android.activity.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.taichi.prompts.android.R
import com.taichi.prompts.android.BR
import com.taichi.prompts.android.databinding.ActivityPhoneBinding
import com.taichi.prompts.android.common.AutoSeparateTextWatcher
import com.taichi.prompts.base.BaseActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PhoneActivity : BaseActivity<ActivityPhoneBinding, PhoneViewModel>() {
    lateinit var formattedNumber : String
    val stringToIntMap: Map<String, String> = mapOf(
        "CODE_GENERATE_FAILED" to "验证码生成失败，需要重新发送",
        "SEND_FAILED" to "验证码发送失败，需要重新发送",
        "ALREADY_SEND" to "验证码已发送，不需要再发送",
        "SEND_SUCCESS" to "验证码发送成功"
    )
    override fun getLayoutId(): Int {
        return R.layout.activity_phone
    }

    override fun getViewModelId(): Int {
        return BR.phoneVm
    }

    override fun initViewData() {
        val layout: RelativeLayout = findViewById(R.id.button)
        val editText: EditText = findViewById(R.id.place)
        val textWatcher = AutoSeparateTextWatcher(editText)
        textWatcher.setRULES(intArrayOf(3, 4, 4))
        textWatcher.setSeparator(' ')
        editText.addTextChangedListener(textWatcher)
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let { text ->
                    if (text.length == 1) {
                        val layout : RelativeLayout = findViewById(R.id.field_defau)
                        layout.setBackgroundResource(R.drawable.field_defau_green)
                    } else if (text.length == 0){
                        val layout : RelativeLayout = findViewById(R.id.field_defau)
                        layout.setBackgroundResource(R.drawable.field_defau)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        layout.setOnClickListener {
            val inputText: String? = editText.text.toString()
            if (inputText != null && inputText.length == 13) {
                formattedNumber = formatPhoneNumber(inputText)
                viewModel?.sendMessage("+86", formattedNumber)

            } else {
                GlobalScope.launch(Dispatchers.Main) {
                    ToastUtils.showShort("请输入正确手机号~")
                }
            }
        }
        val img : ImageView = findViewById(R.id.arrayleft)
        img.setOnClickListener{
            finish()
        }
        viewModel?.openNewActivityEvent?.observe(this, Observer { event ->
            if (event.length > 0 && stringToIntMap.containsKey(event)) {
                GlobalScope.launch(Dispatchers.Main) {
                    ToastUtils.showShort(stringToIntMap[event])
                }
                if (event == "SEND_SUCCESS") {
                    val intent = Intent(this, VerifyActivity::class.java)
                    intent.putExtra("phoneNumber", formattedNumber)
                    startActivity(intent)
                }
            }
        })
    }

    fun formatPhoneNumber(phoneNumber: String): String {
        val noSpaces = phoneNumber.replace(" ", "")
        return noSpaces
    }
}