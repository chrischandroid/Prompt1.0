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
import com.taichi.prompts.android.repository.data.QuestionConfigVO
import com.taichi.prompts.android.repository.data.UserBaseVO
import com.taichi.prompts.android.repository.data.UserQuestionAnswerVO
import com.taichi.prompts.android.repository.data.UserQuestionInfoVO
import com.taichi.prompts.android.repository.data.UserQuestionnaireVO
import com.taichi.prompts.android.repository.data.UserSimpleInfoRequest
import com.taichi.prompts.base.BaseViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate

class TabViewModel(application: Application) : BaseViewModel(application) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateProfile(key: Int, value: String, config1: QuestionConfigVO?, config2: QuestionConfigVO?) {
        val name = SPUtils.getInstance().getString(SP_USER_NICKNAME)
        val birth = SPUtils.getInstance().getString(SP_USER_BIRTH)

        val currentYear = LocalDate.now().year
        val birthYear = if (birth.isNotEmpty()) birth.substring(0, 4).toInt() else 2000
        val age = currentYear - birthYear
        val id = SPUtils.getInstance().getString(SP_USER_ID)
        val genderNum: Int = SPUtils.getInstance().getInt(SP_USER_GENDER)

        val question1 = UserQuestionInfoVO(
            config1?.id ?: 3,
            ""
        )
        val answer1 = UserQuestionAnswerVO(
            config1!!.availableResultVOList[key]?.key ?: "NF",
            config1.availableResultVOList[key]?.label ?: "理想，梦想",
            config1.availableResultVOList[key]?.MBTI_introvert_score ?: 0,
            config1.availableResultVOList[key]?.MBTI_intuition_score ?: 5,
            config1.availableResultVOList[key]?.MBTI_feeling_score ?: 5,
            config1.availableResultVOList[key]?.MBTI_perceiving_score ?: 0,
        )

        val question2 = UserQuestionInfoVO(
            config2?.id ?: 56,
            config2?.questionContent ?: "如果可以马上尝试一件冒险的事情，你会选择什么？:"
        )
        val answer2= UserQuestionAnswerVO(
            "",
            value,
            0,0,0,0
        )
        val info = UserSimpleInfoRequest(
            name,
            "",
            age,
            genderNum,
            "",
            "mbti_quest",
            301,
            listOf(UserQuestionnaireVO(question1, answer1), UserQuestionnaireVO(question2, answer2))
        )
        val token = SPUtils.getInstance().getString(Constants.SP_USER_TOKEN)
        viewModelScope.launch {
            val data: UserBaseVO? = Repository.updateSimpleProfile(info, token)
        }
    }
}
