package com.taichi.prompts.android.activity.home

import android.R
import android.app.Application
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.taichi.prompts.android.repository.Repository
import com.taichi.prompts.android.repository.data.UserSocialLikeRequest
import com.taichi.prompts.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class InfoViewModel(application: Application) : BaseViewModel(application) {
    fun admire(request : UserSocialLikeRequest) {
        viewModelScope.launch {
            val data: Boolean? = Repository.admire(request)
            if (data != null && data == true) {
                GlobalScope.launch(Dispatchers.Main) {
                    ToastUtils.showShort("已成功送出喜欢！")
                }
            }
        }
    }
}