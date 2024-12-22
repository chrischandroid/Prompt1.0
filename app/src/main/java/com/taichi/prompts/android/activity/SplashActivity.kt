package com.taichi.prompts.android.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.SPUtils
import com.taichi.prompts.android.R
import com.taichi.prompts.android.BR
import com.taichi.prompts.android.databinding.ActivitySplashBinding
import com.taichi.prompts.android.activity.home.TabActivity
import com.taichi.prompts.android.activity.login.SplashViewModel
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.base.BaseActivity

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>()  {
    private val SPLASH_TIME_OUT = 3000L
    private val handler = Handler()
    private val runnable = Runnable {
        val i = Intent(this@SplashActivity, PrivacyLoginActivity::class.java)
        startActivity(i)
        finish()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun getViewModelId(): Int {
        return BR.splashVm
    }

    override fun initViewData() {
        handler.postDelayed(runnable, SPLASH_TIME_OUT)

        viewModel?.openNewActivityEvent?.observe(this, Observer { event ->
            if (event == true) {
                Log.e("Prompt------------", "removeCallbacks")
                handler.removeCallbacks(runnable)
                val intent = Intent(this@SplashActivity, TabActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
        val phone = SPUtils.getInstance().getString(Constants.SP_USER_PHONE)
        val token = SPUtils.getInstance().getString(Constants.SP_USER_TOKEN)
        if (phone.isNotEmpty() && token.isNotEmpty()) {
            Log.e("Prompt------------", "loginWithToken")
            viewModel?.loginWithToken(phone, token)
        }
    }
}