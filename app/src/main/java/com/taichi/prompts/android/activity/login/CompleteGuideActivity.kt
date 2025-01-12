package com.taichi.prompts.android.activity.login

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.taichi.prompts.android.R
import com.taichi.prompts.android.activity.home.TabActivity
import com.taichi.prompts.android.common.Constants.SP_USER_BIRTH
import com.taichi.prompts.android.common.Constants.SP_USER_NICKNAME
import com.tencent.qcloud.tuicore.util.SPUtils

class CompleteGuideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_complete_guide)

        val name = intent.getStringExtra("name")
        val type = intent.getIntExtra("type", 1)
        if (type == 2) {
            val topView : TextView = findViewById(R.id.topview)
            topView.text = "好久不见！"
            val bottomView : TextView = findViewById(R.id.bottomView)
            bottomView.text = "还是老样子！我为你准备了一些新的人选，\n正在为您加速准备中…"
            val greetingView : TextView = findViewById(R.id.greetingView)
            greetingView.text = name + "!好久不见！"
            val openView : TextView = findViewById(R.id.open_new_home)
            openView.text = "开启匹配"
        } else {
            val greetingView : TextView = findViewById(R.id.greetingView)
            greetingView.text = "太好啦！" + name + "欢迎开启灵启Prompts！"

            val fullText = "记得先去完善自己的资料，这样才能让别人看见你哦~️"
            val spannableString = SpannableString(fullText)

            val targetText = "完善自己的资料"
            val startIndex = fullText.indexOf(targetText)
            val endIndex = startIndex + targetText.length

            val context = this
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    val intent = Intent(context, SimpleAskActivity::class.java)
                    intent.putExtra("nickname", SPUtils.getInstance().getString(SP_USER_NICKNAME))
                    startActivity(intent)
                }

                override fun updateDrawState(ds: android.text.TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = getResources().getColor(R.color.green_300)
                    ds.isUnderlineText = false
                }
            }

            spannableString.setSpan(clickableSpan, startIndex, endIndex, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)

            val bottomView : TextView = findViewById(R.id.bottomView)

            bottomView.text = spannableString
            bottomView.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        }
        val layout : RelativeLayout = findViewById(R.id.button)
        layout.setOnClickListener {
            val intent = Intent(this, TabActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.putExtra("nickname", SPUtils.getInstance().getString(SP_USER_NICKNAME))
            if (type == 2) {
                intent.putExtra("olduser", "yes")
            }
            startActivity(intent)
        }

    }
}