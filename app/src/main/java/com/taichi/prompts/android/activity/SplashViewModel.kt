package com.taichi.prompts.android.activity.login

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.android.repository.Repository
import com.taichi.prompts.android.repository.data.UserLoginResponse

import com.taichi.prompts.base.BaseViewModel
import kotlinx.coroutines.launch


class SplashViewModel(application: Application) : BaseViewModel(application) {
    private val _openNewActivityEvent = MutableLiveData<Boolean>()
    val openNewActivityEvent: LiveData<Boolean> get() = _openNewActivityEvent

    fun loginWithToken(number: String, token : String) {
        viewModelScope.launch {
            val data: UserLoginResponse? = Repository.loginWithToken(number, token)
            if (data != null) {
                _openNewActivityEvent.value = true
            }
        }
    }

}
