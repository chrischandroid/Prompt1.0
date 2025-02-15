package com.taichi.prompts.android.fragment.mine

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.SPUtils
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.android.repository.Repository
import com.taichi.prompts.android.repository.data.UserBaseDTO
import com.taichi.prompts.android.repository.data.UserBaseInfoRequest
import com.taichi.prompts.base.BaseViewModel
import com.taichi.prompts.base.SingleLiveEvent
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : BaseViewModel(application) {
    fun updateProfile() {
        val id = SPUtils.getInstance().getString(Constants.SP_USER_ID)
        val name = SPUtils.getInstance().getString("userNickName")
        val url = SPUtils.getInstance().getString("headImgUrl")
        val cityName = SPUtils.getInstance().getString("city")
        val hometownStr = SPUtils.getInstance().getString("hometown")
        val career = SPUtils.getInstance().getString("career")
        val school = SPUtils.getInstance().getString("school")
        val asset = SPUtils.getInstance().getString("asset")
        val info = UserBaseInfoRequest(id, name, url, 18, "1999-9-9", 0, 0, cityName, 0, hometownStr, "0", "0", listOf("apple", "banana", "cherry"), school, 180, 50, "", "", "", "", asset)
        val token = SPUtils.getInstance().getString(Constants.SP_USER_TOKEN)
        viewModelScope.launch {
            val data: String = Repository.updateProfile(info, token)
        }
    }

}
