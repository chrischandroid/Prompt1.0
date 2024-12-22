package com.taichi.prompts.android.activity.login

import android.content.Intent
import android.os.Bundle
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
import com.taichi.prompts.android.R
import com.taichi.prompts.android.BR
import com.taichi.prompts.android.activity.home.TabActivity
import com.taichi.prompts.base.BaseActivity

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
            if (event.userBaseVO != null &&  event.userBaseVO.userId != null) {
                Log.i("Prompt", "userid:" + event.userBaseVO.userId)
                val intent = Intent(this@VerifyActivity, TabActivity::class.java)
                startActivity(intent)
            } else {
                Log.i("Prompt", "moveToNewStarterScreen:")
                val intent = Intent(this@VerifyActivity, TabActivity::class.java)
                startActivity(intent)
            }
        })
    }
}