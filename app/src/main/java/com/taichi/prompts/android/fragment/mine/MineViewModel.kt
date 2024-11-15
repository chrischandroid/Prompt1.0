package com.taichi.prompts.android.fragment.mine

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.SPUtils
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.android.repository.Repository
import com.taichi.prompts.base.BaseViewModel
import com.taichi.prompts.base.SingleLiveEvent
import kotlinx.coroutines.launch

class MineViewModel(application: Application) : BaseViewModel(application) {
    val logoutState = SingleLiveEvent<Boolean>()
    val loginState = ObservableField<Boolean>(false)
    val username = ObservableField<String>()

    init {
        val name = SPUtils.getInstance().getString(Constants.SP_USER_NAME)
        if (name.isNotEmpty()) {
            loginState.set(true)
            username.set(name)
        } else {
            loginState.set(false)
            username.set("未登录")
        }
    }

    fun logOut() {
        viewModelScope.launch {
            //val data = Repository.logout()
            //if (data) {
                SPUtils.getInstance().clear()
                loginState.set(false)
                username.set("未登录")
            //}
            logoutState.postValue(true)
        }
    }
}
