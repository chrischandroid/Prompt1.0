package com.taichi.prompts.android.activity.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.taichi.prompts.android.R
import com.taichi.prompts.android.activity.home.TabActivity

class VerifyActivity : AppCompatActivity() {
    private lateinit var inputFields: Array<EditText>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_verify)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 初始化EditText字段
        inputFields = Array(6) { EditText(this) }
        val formLayout: LinearLayout = findViewById(R.id.form)
        for (i in 0 until 6) {
            val id = resources.getIdentifier("input_${i + 1}", "id", packageName)
            inputFields[i] =
                formLayout.findViewById(id) ?: throw RuntimeException("ID $id not found.")

            // 为每个EditText添加TextWatcher
            inputFields[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    s?.let {
                        if (it.length == 1) {
                            // 移动到下一个EditText
                            if (inputFields[i].nextFocusDownId != View.NO_ID) {
                                inputFields[i + 1].requestFocus()
                            } else if (i == 5) {
                                moveToNextScreen()
                            }
                        }
                    }
                }
            })

        }
    }

    private fun moveToNextScreen() {
        val intent = Intent(this, TabActivity::class.java)
        startActivity(intent)
    }
}