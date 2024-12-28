package com.taichi.prompts.android.activity.login

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.WindowInsets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.taichi.prompts.android.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WelcomeGuideActivity : AppCompatActivity() {
    private lateinit var bubble: RelativeLayout
    private lateinit var bubble1: RelativeLayout
    private lateinit var bubble2: RelativeLayout
    private lateinit var bubble3: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome_guide)

        bubble = findViewById(R.id.bubble)
        bubble1 = findViewById(R.id.bubble1)
        bubble2 = findViewById(R.id.bubble2)
        bubble3 = findViewById(R.id.frame_16244)

        // 初始设置为不可见
        bubble.visibility = View.GONE
        bubble1.visibility = View.GONE
        bubble2.visibility = View.GONE
        bubble3.visibility = View.GONE
        lifecycleScope.launch {
            showViewsWithDelay()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private suspend fun showViewsWithDelay() {
        withContext(Dispatchers.Main) {
            delay(1000)
            bubble.visibility = View.VISIBLE
            delay(1000)
            bubble1.visibility = View.VISIBLE
            delay(1000)
            bubble2.visibility = View.VISIBLE
            delay(1000)
            bubble3.visibility = View.VISIBLE
            delay(1000)
            val dialog = Dialog(this@WelcomeGuideActivity)
            dialog.setContentView(R.layout.custom_dialog)
            val window = dialog.window
            if (window!= null) {
                window.setGravity(Gravity.BOTTOM)  // 设置对话框显示位置为底部
                val layoutParams = window.attributes
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT  // 设置宽度占满屏幕
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT  // 高度自适应内容
                window.attributes = layoutParams
            }
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            val boy = dialog.findViewById<Button>(R.id.buttonm)
            val girl = dialog.findViewById<Button>(R.id.buttonf)
            boy.setOnClickListener {
                dialog.dismiss()
            }
            girl.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }
}