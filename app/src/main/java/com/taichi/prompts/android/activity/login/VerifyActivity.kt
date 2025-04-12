package com.taichi.prompts.android.activity.login

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.taichi.prompts.android.databinding.ActivityVerifyBinding
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.SPUtils
import com.taichi.prompts.android.R
import com.taichi.prompts.android.BR
import com.taichi.prompts.android.activity.home.TabActivity
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.base.BaseActivity
import java.util.ArrayList

class VerifyActivity : BaseActivity<ActivityVerifyBinding, VerifyViewModel>() {
    private lateinit var inputFields: Array<EditText>
    override fun getLayoutId(): Int {
        return R.layout.activity_verify
    }

    override fun getViewModelId(): Int {
        return BR.verifyVm
    }

    override fun initViewData() {
        val intent = intent
        val phoneNumber: String? = intent?.getStringExtra("phoneNumber")
        val hasSentTextView: TextView = findViewById(R.id.has_sent)

        if (phoneNumber != null) {
            hasSentTextView.text = "已发送至 $phoneNumber"
        }

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

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    s?.let {
                        if (it.length == 1) {
                            inputFields[i].setBackgroundResource(R.drawable.input_deful)
                            // 移动到下一个EditText
                            if (inputFields[i].nextFocusDownId != View.NO_ID) {
                                inputFields[i + 1].requestFocus()
                            } else if (i == 5) {
                                var password : String = ""
                                for (j in 0 until 6) {
                                    password += inputFields[j].text.toString()
                                }
                                viewModel?.loginMessage("+86"+ phoneNumber, password)
                            }
                        }
                    }
                }
            })

        }

        val img : ImageView = findViewById(R.id.arrayleft1)
        img.setOnClickListener{
            finish()
        }
        val modify : TextView = findViewById(R.id.modify)
        modify.setOnClickListener{
            finish()
        }

        viewModel?.openNewActivityEvent?.observe(this, Observer { event ->
            if (event.userBaseVO != null) {
                SPUtils.getInstance().put(Constants.SP_USER_ID, event.userBaseVO.userId)
                SPUtils.getInstance().put("userNickName", event.userBaseVO.userNickName)
                SPUtils.getInstance().put("headImgUrl", event.userBaseVO.headImgUrl)
                SPUtils.getInstance().put("city", event.userBaseVO.city)
                SPUtils.getInstance().put("hometown",event.userBaseVO.hometown)
                SPUtils.getInstance().put("career", event.userBaseVO.career)
                SPUtils.getInstance().put("school", event.userBaseVO.school)
                SPUtils.getInstance().put("asset", event.userBaseVO.asset)
                SPUtils.getInstance().put("gender", event.userBaseVO.gender)
                SPUtils.getInstance().put("age", event.userBaseVO.age)
                SPUtils.getInstance().put("weight", event.userBaseVO.weight)
                SPUtils.getInstance().put("height", event.userBaseVO.height)
            }
            if (event.userBaseVO != null &&  event.userBaseVO.userNickName != null && event.userBaseVO.userNickName.length > 0) {
                val intent = Intent(this@VerifyActivity, CompleteGuideActivity::class.java)
                intent.putExtra("name", event.userBaseVO.userNickName)
                intent.putExtra("type", 2)
                startActivity(intent)
            } else {
                val id = SPUtils.getInstance().getString(Constants.SP_USER_TOKEN)
                viewModel?.requestQuestion(id, "mbti_quest", 301)
                viewModel?.requestQuestion(id, "mbti_quest", 201)
                Log.i("Prompt", "moveToNewStarterScreen:")

                viewModel?.question_2?.observe(this, Observer { event ->
                    val intent = Intent(this@VerifyActivity, WelcomeGuideActivity::class.java)
                    intent.putParcelableArrayListExtra("question1",
                        viewModel?.question_1?.value as ArrayList<out Parcelable?>?
                    )
                    intent.putParcelableArrayListExtra("question2",
                        viewModel?.question_2?.value as ArrayList<out Parcelable?>?
                    )
                    startActivity(intent)
                })
            }
        })
    }
}