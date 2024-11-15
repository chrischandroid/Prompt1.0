package com.taichi.prompts.android.activity.login

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.android.repository.Repository
import com.taichi.prompts.android.repository.data.RegisterData
import com.taichi.prompts.android.repository.data.UserData
import com.taichi.prompts.base.BaseViewModel
import com.taichi.prompts.base.SingleLiveEvent
import com.tencent.qcloud.tuicore.TUILogin
import com.tencent.qcloud.tuicore.interfaces.TUICallback
import com.tencent.qcloud.tuicore.util.GenerateTestUserSig
import kotlinx.coroutines.launch
import java.security.AccessController.getContext

class LoginViewModel(application: Application) : BaseViewModel(application) {

    val username = ObservableField<String>()
    val password = ObservableField<String>()
    val passwordTwice = ObservableField<String>()

    //登录或者注册是否成功
    val actionState = SingleLiveEvent<Boolean>()

    init {

    }

    /**
     * 登录
     */
    fun login() {
        if (checkNull(username.get()) || checkNull(password.get())) {
            ToastUtils.showShort("输入不能为空")
            return
        }
        viewModelScope.launch {
            val data: String = Repository.login(username.get() ?: "", password.get() ?: "")
            if (data.length == 16) {
                //保存用户名
                SPUtils.getInstance().put(Constants.SP_USER_ID, data)
                SPUtils.getInstance().put(Constants.SP_USER_NAME, username.get())
                //登录成功
                actionState.postValue(true)
            } else {
                actionState.postValue(false)
            }
        }
    }

    /**
     * 获取UserSig
     */
    fun getUserSig() {
        val data: String = SPUtils.getInstance().getString(Constants.SP_USER_ID)
        if (data.isEmpty()) {
            return
        }
        viewModelScope.launch {
            val userSig: String = Repository.getUserSig(data)
            if (userSig.length > 0) {
                Log.e("TAG", "SIG-------------------"+ userSig)
                SPUtils.getInstance().put(Constants.SP_USER_SID, userSig)
            }
        }
    }

    /**
     * 注册
     */
    fun register() {
        if (checkNull(username.get()) || checkNull(password.get()) || checkNull(passwordTwice.get())) {
            ToastUtils.showShort("输入不能为空")
            return
        }
        viewModelScope.launch {
            val data: String = Repository.register(
                username.get() ?: "",
                password.get() ?: "",
                passwordTwice.get() ?: ""
            )
            if (data.length == 16) {
                //注册成功
                actionState.postValue(true)
            } else {
                actionState.postValue(false)
            }
        }
    }

    private fun checkNull(value: String?): Boolean {
        return value.isNullOrEmpty()
    }

}
