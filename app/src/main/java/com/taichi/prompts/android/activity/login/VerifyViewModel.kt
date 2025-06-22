package com.taichi.prompts.android.activity.login

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.android.repository.Repository
import com.taichi.prompts.android.repository.data.QuestionConfigVO
import com.taichi.prompts.android.repository.data.UserLoginVO

import com.taichi.prompts.base.BaseViewModel
import kotlinx.coroutines.launch


class VerifyViewModel(application: Application) : BaseViewModel(application) {
    private val _openNewActivityEvent = MutableLiveData<UserLoginVO>()
    val openNewActivityEvent: LiveData<UserLoginVO> get() = _openNewActivityEvent

    private val question1 = MutableLiveData<List<QuestionConfigVO>>()
    val question_1: LiveData<List<QuestionConfigVO>> get() = question1

    private val question2 = MutableLiveData<List<QuestionConfigVO>>()
    val question_2: LiveData<List<QuestionConfigVO>> get() = question2

    fun loginMessage(number: String, code : String) {
        viewModelScope.launch {
            val data: UserLoginVO? = Repository.loginMessage(number, code)
            if (data != null) {
                SPUtils.getInstance().put(Constants.SP_USER_PHONE, number)
                _openNewActivityEvent.value = data
            }
        }
    }

    fun requestQuestion(token : String, template: String, type : Int) {
        viewModelScope.launch {
            val data: List<QuestionConfigVO>? = Repository.getQuestion(token, template, type)
            if (data != null) {
                if (type == 301) {
                    question1.value = data
                } else {
                    question2.value = data
                }
            }
        }
    }

    fun getUserSig(token : String) {
        viewModelScope.launch {
            val userSig: String = Repository.getUserSig(token)
            if (userSig.length > 0) {
                Log.e("TUI", "SIG---"+ userSig)
                SPUtils.getInstance().put(Constants.SP_USER_SID, userSig)
            }
        }
    }

}
