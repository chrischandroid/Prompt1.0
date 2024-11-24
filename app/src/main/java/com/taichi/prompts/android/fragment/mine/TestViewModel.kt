package com.taichi.prompts.android.fragment.mine

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.android.repository.Repository
import com.taichi.prompts.android.repository.data.MbtiResultVO
import com.taichi.prompts.android.repository.data.UserProfileVO
import com.taichi.prompts.base.BaseViewModel
import com.taichi.prompts.base.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TestViewModel(application: Application) : BaseViewModel(application) {
    var mbti = SingleLiveEvent<String>()
    fun getUserMBTI(id : String) {
        viewModelScope.launch {
            val data: MbtiResultVO? = Repository.getUserMBTI(id)
            if (data != null && data.mbtiType.length == 4) {
                SPUtils.getInstance().put(Constants.SP_USER_MBTJ, data.mbtiType)
                mbti.postValue(data.mbtiType)
            }
        }
    }
}