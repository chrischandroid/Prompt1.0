package com.taichi.prompts.android.activity.login

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.taichi.prompts.android.R
import com.taichi.prompts.android.activity.home.TabActivity

class PhoneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_phone)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val layout: RelativeLayout = findViewById(R.id.button)
        layout.setOnClickListener {
            val editText: EditText = findViewById(R.id.place)
            val inputText: String? = editText.text.toString()
            if (inputText != null && inputText.equals("18000000000")) {
                val intent = Intent(this, TabActivity::class.java)
                startActivity(intent)
            }
        }
    }
}