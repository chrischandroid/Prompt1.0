package com.taichi.prompts.android.fragment.mine

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.SPUtils
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.android.repository.Repository
import com.taichi.prompts.android.repository.data.UserBaseInfoRequest
import com.taichi.prompts.base.BaseViewModel
import com.taichi.prompts.base.SingleLiveEvent
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : BaseViewModel(application) {
    fun updateProfile() {
        val token = SPUtils.getInstance().getString(Constants.SP_USER_TOKEN)
        val id = SPUtils.getInstance().getString(Constants.SP_USER_ID)
        val name = SPUtils.getInstance().getString("userNickName")
        var url = SPUtils.getInstance().getString("headImgUrl")
        if (url != null && url.contains("file:///")) {
            viewModelScope.launch {
                val newUrl: String = Repository.updateImg(url, token)
                SPUtils.getInstance().put("headImgUrl", newUrl)
                url = newUrl
            }
        }
        val cityName = SPUtils.getInstance().getString("city")
        val hometownStr = SPUtils.getInstance().getString("hometown")
        val career = SPUtils.getInstance().getString("career")
        val school = SPUtils.getInstance().getString("school")
        val asset = SPUtils.getInstance().getString("asset")
        val birth = SPUtils.getInstance().getString(Constants.SP_USER_BIRTH)
        val height = SPUtils.getInstance().getInt("height", 170)
        val weight = SPUtils.getInstance().getInt("weight", 50)
        val age = SPUtils.getInstance().getInt("age", 0)
        val gender = SPUtils.getInstance().getInt(Constants.SP_USER_GENDER, 1)
        val info = UserBaseInfoRequest(id, name, url, age, birth, 0, gender, cityName, 0, hometownStr, "0", "0", listOf("apple", "banana", "cherry"), school, height, weight, career, "", "", "", asset)
        viewModelScope.launch {
            val data: String = Repository.updateProfile(info, token)
        }
    }

}
