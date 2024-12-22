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

import com.taichi.prompts.base.BaseViewModel
import kotlinx.coroutines.launch


class PhoneViewModel(application: Application) : BaseViewModel(application) {
    private val _openNewActivityEvent = MutableLiveData<String>()
    val openNewActivityEvent: LiveData<String> get() = _openNewActivityEvent

    fun sendMessage(region: String, number : String) {
        viewModelScope.launch {
            val data: String = Repository.sendMessage(region, number)
            if (data.length > 0) {
                _openNewActivityEvent.value = data
            }
        }
    }

}
