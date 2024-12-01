package com.taichi.prompts.android.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.taichi.prompts.android.R
import com.taichi.prompts.android.activity.home.TabActivity

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT = 3000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        enableEdgeToEdge()
        Handler().postDelayed({
            val i = Intent(this, PrivacyLoginActivity::class.java)
            startActivity(i)
            finish()
        }, SPLASH_TIME_OUT)
    }
}