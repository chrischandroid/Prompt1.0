package com.taichi.prompts.android.activity.login

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.transition.Visibility
import com.blankj.utilcode.util.SPUtils
import com.taichi.prompts.android.R
import com.taichi.prompts.android.activity.home.TabActivity
import com.taichi.prompts.android.common.Constants.SP_USER_NICKNAME

class SimpleAskActivity : AppCompatActivity() {
    var lastCheck = 1
    var nowcheck = 1
    var lastlay : RelativeLayout = findViewById(R.id.relativeLayout1)
    var lastyes : RelativeLayout = findViewById(R.id.icon1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_ask)
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
        initClick()
        initResult()
    }

    private fun initResult() {
        val layout : RelativeLayout = findViewById(R.id.relativeLayout1)
        val yes : RelativeLayout = findViewById(R.id.icon1)
        val drawable: Drawable? = ContextCompat.getDrawable(this, R.drawable.collum)
        layout.background = drawable
        yes.visibility = View.VISIBLE
    }

    private fun initClick() {
        val finish : RelativeLayout = findViewById(R.id.array)
        finish.setOnClickListener{
            finish()
        }
        val skip : RelativeLayout = findViewById(R.id.button6)
        skip.setOnClickListener{
            val intent = Intent(this, TabActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.putExtra("nickname", SPUtils.getInstance().getString(SP_USER_NICKNAME))
            startActivity(intent)
        }
        val next : RelativeLayout = findViewById(R.id.button1)
        next.setOnClickListener{
            val intent = Intent(this, SimpleAnswerActivity::class.java)
            intent.putExtra("key", lastCheck)
            intent.putExtra("nickname", SPUtils.getInstance().getString(SP_USER_NICKNAME))
            startActivity(intent)
        }
        val layout1 : RelativeLayout = findViewById(R.id.relativeLayout1)
        val layout2 : RelativeLayout = findViewById(R.id.relativeLayout2)
        val layout3 : RelativeLayout = findViewById(R.id.relativeLayout3)
        val layout4 : RelativeLayout = findViewById(R.id.relativeLayout4)
        layout1.setOnClickListener{
            nowcheck = 1
            val layout : RelativeLayout = findViewById(R.id.relativeLayout1)
            val yes : RelativeLayout = findViewById(R.id.icon1)
            val drawable: Drawable? = ContextCompat.getDrawable(this, R.drawable.collum)
            layout.background = drawable
            yes.visibility = View.VISIBLE
            if (nowcheck != lastCheck) {
                val drawable: Drawable? = ContextCompat.getDrawable(this, R.drawable.collum_1)
                lastlay.background = drawable
                lastyes.visibility = View.INVISIBLE
                lastCheck = nowcheck
                lastlay = layout
                lastyes = yes
            }
        }
        layout2.setOnClickListener{
            nowcheck = 2
            val layout : RelativeLayout = findViewById(R.id.relativeLayout2)
            val yes : RelativeLayout = findViewById(R.id.icon2)
            val drawable: Drawable? = ContextCompat.getDrawable(this, R.drawable.collum)
            layout.background = drawable
            yes.visibility = View.VISIBLE
            if (nowcheck != lastCheck) {
                val drawable: Drawable? = ContextCompat.getDrawable(this, R.drawable.collum_1)
                lastlay.background = drawable
                lastyes.visibility = View.INVISIBLE
                lastCheck = nowcheck
                lastlay = layout
                lastyes = yes
            }
        }
        layout3.setOnClickListener{
            nowcheck = 3
            val layout : RelativeLayout = findViewById(R.id.relativeLayout3)
            val yes : RelativeLayout = findViewById(R.id.icon3)
            val drawable: Drawable? = ContextCompat.getDrawable(this, R.drawable.collum)
            layout.background = drawable
            yes.visibility = View.VISIBLE
            if (nowcheck != lastCheck) {
                val drawable: Drawable? = ContextCompat.getDrawable(this, R.drawable.collum_1)
                lastlay.background = drawable
                lastyes.visibility = View.INVISIBLE
                lastCheck = nowcheck
                lastlay = layout
                lastyes = yes
            }
        }
        layout4.setOnClickListener{
            nowcheck = 4
            val layout : RelativeLayout = findViewById(R.id.relativeLayout4)
            val yes : RelativeLayout = findViewById(R.id.icon4)
            val drawable: Drawable? = ContextCompat.getDrawable(this, R.drawable.collum)
            layout.background = drawable
            yes.visibility = View.VISIBLE
            if (nowcheck != lastCheck) {
                val drawable: Drawable? = ContextCompat.getDrawable(this, R.drawable.collum_1)
                lastlay.background = drawable
                lastyes.visibility = View.INVISIBLE
                lastCheck = nowcheck
                lastlay = layout
                lastyes = yes
            }
        }
    }
}