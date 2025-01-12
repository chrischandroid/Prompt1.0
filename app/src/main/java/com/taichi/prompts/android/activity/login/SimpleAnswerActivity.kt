package com.taichi.prompts.android.activity.login

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.taichi.prompts.android.R
import com.taichi.prompts.android.activity.home.TabActivity
import org.w3c.dom.Text

class SimpleAnswerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_simple_answer)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val name : String? = intent.getStringExtra("nickname")
        if (name != null) {
            val view : TextView = findViewById(R.id.user_)
            view.text = name
        }
        val key : Int = intent.getIntExtra("key", 1)
        val map : Map<Int, String> = mapOf(
            1 to "面对复杂问题时，你是如何拆解并找到解决方案的？",
            2 to "你觉得什么样的瞬间会让两个人有心灵的共鸣？",
            3 to "在你的生活中，哪一条原则或传统是你最珍视的?",
            4 to "你最疯狂或勇敢的一次冒险经历是什么?"
        )
        val que : TextView = findViewById(R.id.q_id)
        que.text = map[key]
        val finish : RelativeLayout = findViewById(R.id.array)
        finish.setOnClickListener{
            finish()
        }
        val editText: EditText = findViewById(R.id.qw4)
        val button: RelativeLayout = findViewById(R.id.button1)
        button.setOnClickListener{
            val inputText = editText.text.toString()
            if (inputText.isNotEmpty()) {
                val intent = Intent(this, TabActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.putExtra("key", map[key])
                intent.putExtra("value", inputText)
                startActivity(intent)
            } else {
                Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show()
            }
        }

    }
}