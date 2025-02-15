package com.taichi.prompts.android.fragment.mine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.taichi.prompts.android.fragment.mine.ui.main.ProfileFragment
import com.taichi.prompts.android.R

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ProfileFragment.newInstance())
                .commitNow()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}