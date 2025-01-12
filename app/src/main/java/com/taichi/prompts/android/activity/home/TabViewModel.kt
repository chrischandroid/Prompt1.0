package com.taichi.prompts.android.activity.home

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.SPUtils
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.android.common.Constants.SP_USER_BIRTH
import com.taichi.prompts.android.common.Constants.SP_USER_GENDER
import com.taichi.prompts.android.common.Constants.SP_USER_ID
import com.taichi.prompts.android.common.Constants.SP_USER_NICKNAME
import com.taichi.prompts.android.repository.Repository
import com.taichi.prompts.android.repository.data.QuestionInfoVO
import com.taichi.prompts.android.repository.data.UserBaseDTO
import com.taichi.prompts.android.repository.data.UserBaseVO
import com.taichi.prompts.android.repository.data.UserQuestionAnswerVO
import com.taichi.prompts.android.repository.data.UserQuestionInfoVO
import com.taichi.prompts.android.repository.data.UserQuestionnaireVO
import com.taichi.prompts.android.repository.data.UserSimpleInfoRequest
import com.taichi.prompts.base.BaseViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle
import java.util.Locale

class TabViewModel(application: Application) : BaseViewModel(application) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateProfile(key : String, value : String) {
        val name = SPUtils.getInstance().getString(SP_USER_NICKNAME)
        val birth = SPUtils.getInstance().getString(SP_USER_BIRTH)

        val currentYear = LocalDate.now().year
        val birthYear = if (birth.isNotEmpty()) birth.substring(0, 4).toInt() else 2000
        val age = currentYear - birthYear
        val marryNum: Int = 0
        val id = SPUtils.getInstance().getString(SP_USER_ID)
        val genderNum: Int = SPUtils.getInstance().getInt(SP_USER_GENDER)
        val cityName: String = "null"

        val degreeNum: Int = 0
        val hometownStr: String = "null"
        val phoneStr: String = "null"
        val mailStr: String = "null"
        val question = UserQuestionInfoVO(
            0,
            key
        )
        val answer = UserQuestionAnswerVO(
            "key",
            value,
            0,0,0,0
        )
        val info = UserSimpleInfoRequest(
            name,
            "",
            age,
            genderNum,
            "",
            "mbti",
            0,
            listOf(UserQuestionnaireVO(question, answer))
        )
        val token = SPUtils.getInstance().getString(Constants.SP_USER_TOKEN)
        viewModelScope.launch {
            val data: UserBaseVO? = Repository.updateSimpleProfile(info, token)
        }
    }
}
