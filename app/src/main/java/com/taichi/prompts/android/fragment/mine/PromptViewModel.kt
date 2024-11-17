package com.taichi.prompts.android.fragment.mine

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.taichi.prompts.android.repository.Repository
import com.taichi.prompts.android.repository.data.UserProfileVO
import com.taichi.prompts.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PromptViewModel(application: Application) : BaseViewModel(application) {
    fun saveQuestion(id : String, map : MutableMap<String, String>) {
        viewModelScope.launch {
            try {
                val data: UserProfileVO? = Repository.saveQuestion(id, map)
                if (data != null) {
                    GlobalScope.launch(Dispatchers.Main) {
                        ToastUtils.showShort("Prompt更新成功")
                    }
                }
            } catch (e: HttpException) {
                Log.e("PromptView", e.toString())
            }
        }
    }
}