package com.taichi.prompts.android

import android.app.Application
import com.taichi.prompts.http.RetrofitClient

class Prompt:Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitClient.getInstance().setContext(applicationContext)
    }
}
