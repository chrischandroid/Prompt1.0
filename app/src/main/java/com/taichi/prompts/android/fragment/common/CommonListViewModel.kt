package com.taichi.prompts.android.fragment.common

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.SPUtils
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.android.repository.Repository
import com.taichi.prompts.android.repository.data.UserSeedVO
import com.taichi.prompts.base.BaseViewModel
import com.taichi.prompts.base.SingleLiveEvent
import kotlinx.coroutines.launch

class CommonListViewModel(application: Application) : BaseViewModel(application) {

    var receiveListData = SingleLiveEvent<List<UserSeedVO>?>()
    var sendListData = SingleLiveEvent<List<UserSeedVO>?>()

    public fun getReceiveList() {
        viewModelScope.launch {
            val id = SPUtils.getInstance().getString(Constants.SP_USER_TOKEN)
            if (!id.isEmpty()) {
                val data = Repository.getSeedList(1, id)
                if (data != null) {
                    receiveListData.postValue(data)
                }
            }
        }
    }

    public fun getSendList() {
        viewModelScope.launch {
            val id = SPUtils.getInstance().getString(Constants.SP_USER_TOKEN)
            if (!id.isEmpty()) {
                val data = Repository.getSeedList(2, id)
                if (data != null) {
                    sendListData.postValue(data)
                }
            }
        }
    }
}
