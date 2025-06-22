package com.taichi.prompts.android.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.taichi.prompts.android.R
import com.taichi.prompts.android.BR
import com.taichi.prompts.android.databinding.ActivitySplashBinding
import com.taichi.prompts.android.activity.home.TabActivity
import com.taichi.prompts.android.activity.login.SplashViewModel
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.base.BaseActivity
import java.lang.ref.WeakReference

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>()  {
    private val SPLASH_TIME_OUT = 1500L

    // 使用静态内部类 + 弱引用避免内存泄漏
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        // 使用弱引用持有 Activity
        private val activityRef = WeakReference(this@SplashActivity)

        override fun run() {
            val activity = activityRef.get() ?: return
            val i = Intent(activity, PrivacyLoginActivity::class.java)
            activity.startActivity(i)
            activity.finish()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun getViewModelId(): Int {
        return BR.splashVm
    }

    override fun initViewData() {
        handler.postDelayed(runnable, SPLASH_TIME_OUT)

        // 使用 LifecycleOwner 的方式自动管理观察器生命周期
        viewModel?.openNewActivityEvent?.observe(this) { event ->
            if (event == true) {
                navigateToTabActivity()
            }
        }

        val phone = SPUtils.getInstance().getString(Constants.SP_USER_PHONE)
        val token = SPUtils.getInstance().getString(Constants.SP_USER_TOKEN)
        if (phone.isNotEmpty() && token.isNotEmpty()) {
            viewModel?.loginWithToken(phone, token)
        }
    }

    private fun navigateToTabActivity() {
        handler.removeCallbacks(runnable)
        val intent = Intent(this, TabActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
        ToastUtils.cancel()
    }
}