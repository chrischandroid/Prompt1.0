package com.taichi.prompts.android.fragment.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.SPUtils
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.android.repository.Repository
import com.taichi.prompts.android.repository.data.UserProfileMatchVOList
import com.taichi.prompts.base.BaseViewModel
import com.taichi.prompts.base.SingleLiveEvent
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

class HomeViewModel(application: Application) : BaseViewModel(application) {

    var homeListData = SingleLiveEvent<List<UserProfileMatchVOList>?>()

    init {
        getHomeList()
    }
    public fun getHomeList() {
        viewModelScope.launch {
            val id = SPUtils.getInstance().getString(Constants.SP_USER_TOKEN)
            if (!id.isEmpty()) {
                val data = Repository.getHomeList(id, 300)
                Log.e("--", data?.size.toString())
                if (data != null) {
                    homeListData.postValue(data)
                }
            }
        }

    }
}
