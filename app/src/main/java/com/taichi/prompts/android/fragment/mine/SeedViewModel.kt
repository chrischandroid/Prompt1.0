package com.taichi.prompts.android.fragment.mine

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.SPUtils
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.android.repository.Repository
import com.taichi.prompts.android.repository.data.SeedFromUserVO
import com.taichi.prompts.base.BaseViewModel
import com.taichi.prompts.base.SingleLiveEvent
import kotlinx.coroutines.launch

class SeedViewModel(application: Application) : BaseViewModel(application) {
    var seedData = SingleLiveEvent<SeedFromUserVO?>()

    public fun getSeedDetail(seedId : Long) {
        viewModelScope.launch {
            val id = SPUtils.getInstance().getString(Constants.SP_USER_TOKEN)
            if (!id.isEmpty()) {
                val data = Repository.getSeedFromUser(id, seedId)
                if (data != null) {
                    seedData.postValue(data)
                }
            }
        }
    }
}