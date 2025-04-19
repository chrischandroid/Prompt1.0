package com.taichi.prompts.android.fragment.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.SPUtils
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.android.repository.Repository
import com.taichi.prompts.android.repository.data.UserBaseVO
import com.taichi.prompts.android.repository.data.UserProfileMatchVOList
import com.taichi.prompts.android.repository.data.UserRecVO
import com.taichi.prompts.base.BaseViewModel
import com.taichi.prompts.base.SingleLiveEvent
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

class HomeViewModel(application: Application) : BaseViewModel(application) {

    var homeListData = SingleLiveEvent<List<UserRecVO>?>()
    var recommandData = SingleLiveEvent<UserBaseVO?>()

    init {
        getHomeList()
    }

    public fun getHomeList() {
        viewModelScope.launch {
            val id = SPUtils.getInstance().getString(Constants.SP_USER_TOKEN)
            if (!id.isEmpty()) {
                val data = Repository.getHomeList(id, 300)
                if (data != null) {
                    homeListData.postValue(data.userRecVOList)
                }
            }
        }
    }

    public fun getUserDetail(id : String) {
        viewModelScope.launch {
            val token = SPUtils.getInstance().getString(Constants.SP_USER_TOKEN)
            if (!token.isEmpty()) {
                val data = Repository.getUserDetail(token, id)
                if (data != null) {
                    recommandData.postValue(data)
                }
            }
        }
    }
}
