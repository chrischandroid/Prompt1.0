package com.taichi.prompts.android.fragment.mine

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.taichi.prompts.android.repository.Repository
import com.taichi.prompts.android.repository.data.UserProfileVO
import com.taichi.prompts.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PromptViewModel(application: Application) : BaseViewModel(application) {
    fun saveQuestion(id : String, map : MutableMap<String, String>) {
        viewModelScope.launch {
            val data: UserProfileVO? = Repository.saveQuestion(id, map)
            if (data != null) {
                GlobalScope.launch(Dispatchers.Main) {
                    ToastUtils.showShort("Prompt更新成功")
                }
            }
        }
    }
}