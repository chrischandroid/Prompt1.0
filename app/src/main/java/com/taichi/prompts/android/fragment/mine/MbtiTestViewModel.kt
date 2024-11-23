package com.taichi.prompts.android.fragment.mine

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.android.repository.Repository
import com.taichi.prompts.android.repository.data.QuestionInfoVO
import com.taichi.prompts.android.repository.data.UserProfileVO
import com.taichi.prompts.base.BaseViewModel
import com.taichi.prompts.base.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MbtiTestViewModel(application: Application) : BaseViewModel(application) {
    var qustionData = SingleLiveEvent<List<QuestionInfoVO>?>()

    init {
        getQuestionList()
    }
    public fun getQuestionList() {
        viewModelScope.launch {
            val id = SPUtils.getInstance().getString(Constants.SP_USER_ID)
            if (!id.isEmpty()) {
                val data = Repository.getQuestionList(id, 300)
                if (data != null) {
                    qustionData.postValue(data)
                }
            }
        }

    }
    fun saveMbtiQuestion(id : String, map : MutableMap<String, Pair<String, Long>>) {
        viewModelScope.launch {
            try {
                val data: UserProfileVO? = Repository.saveMbtiQuestion(id, map)
                if (data != null) {
                    GlobalScope.launch(Dispatchers.Main) {
                        ToastUtils.showShort("你是 " + data.profileTag + "!")
                        SPUtils.getInstance().put(Constants.SP_USER_MBTJ, data.profileTag)
                    }
                }
            } catch (e: HttpException) {
                Log.e("PromptView", e.toString())
            }
        }
    }
}