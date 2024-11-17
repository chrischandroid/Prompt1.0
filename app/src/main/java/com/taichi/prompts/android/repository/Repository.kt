package com.taichi.prompts.android.repository

import android.util.Log
import com.blankj.utilcode.util.ToastUtils
import com.taichi.prompts.android.repository.data.QuestionAvailableResultVO
import com.taichi.prompts.android.repository.data.QuestionInfoVO
import com.taichi.prompts.android.repository.data.QuestionnaireResultVO
import com.taichi.prompts.android.repository.data.RegisterRequest
import com.taichi.prompts.android.repository.data.UpdateInfoRequest
import com.taichi.prompts.android.repository.data.UserBaseDTO
import com.taichi.prompts.android.repository.data.UserProfileMatchRequest
import com.taichi.prompts.android.repository.data.UserProfileMatchVOList
import com.taichi.prompts.android.repository.data.UserProfileRequest
import com.taichi.prompts.android.repository.data.UserProfileVO
import com.taichi.prompts.android.repository.data.UserRegisterDTO
import com.taichi.prompts.http.BaseResponse
import com.taichi.prompts.http.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

object Repository {
    private const val Success_Code = 0
    private const val Need_login_Code = -1001
    private const val registerType_name_password = 3

    suspend fun getHomeList(id: String, type : Int): List<UserProfileMatchVOList>? {
        val userProfileMatchRequest = UserProfileMatchRequest(
            id, type, 0, 5
        )
        val data: BaseResponse<List<UserProfileMatchVOList>>? = getDefaultApi().homeList(userProfileMatchRequest)
        return responseCall(data)
    }


    /**
     * 登录
     */
    suspend fun login(username: String, password: String): String {
        val dataList = UserRegisterDTO(3, username, password)
        val registerRequest = RegisterRequest(
            UserRegisterDTO(3, username, password)
        )
        val data: BaseResponse<String> = getDefaultApi().login(registerRequest)
        return responseCall(data).toString()
    }

    /**
     * 注册
     */
    suspend fun register(username: String, password: String, passwordTwice: String): String {
        //val dataList = mapOf("registerAccount" to username, "registerSecret" to password ,"registerType" to 3)
        val dataList = UserRegisterDTO(3, username, password)
        val registerRequest = RegisterRequest(
            UserRegisterDTO(3, username, password)
        )
        val data: BaseResponse<String> = getDefaultApi().register(registerRequest)
        return responseCall(data).toString()
    }

    /**
     * 更新信息
     */
    suspend fun updateProfile(info : UserBaseDTO): String {
        val registerRequest = UpdateInfoRequest(info)
        val data: BaseResponse<String> = getDefaultApi().updateInfo(registerRequest)
        return responseCall(data).toString()
    }

    /**
     * 获取SigId
     */
    suspend fun getUserSig(id : String) : String {
        val data: BaseResponse<String> = getDefaultApi().getUserSig(id)
        return responseCall(data).toString()
    }
    /**
     * 保存prompt
     */
    suspend fun saveQuestion(id: String, map: MutableMap<String, String>): UserProfileVO? {
        val questionnaireResult : List<QuestionnaireResultVO> = createQuestionnaireResult(map)
        val questionnaireSnapshot : List<String>? = null
        val userProfileRequest = UserProfileRequest(id, "mbti_quest", 200, questionnaireResult, questionnaireSnapshot)
        try {
            val data: BaseResponse<UserProfileVO?>?= getDefaultApi().updatePrompt(userProfileRequest)
            return responseCall(data)
        } catch (e : HttpException) {
            return null
        }
    }

    /**
     * 登出
     */
    suspend fun logout(): Boolean {
        val data: BaseResponse<Any>? = getDefaultApi().logout()
        return responseNoDataCall(data)
    }

    /**
     * 返回值处理(无data返回情况)
     */
    private fun responseNoDataCall(
        response: BaseResponse<Any>?,
        needLogin: (() -> Unit?)? = null
    ): Boolean {
        if (response == null) {
            GlobalScope.launch(Dispatchers.Main) {
                ToastUtils.showShort("请求异常")
            }
            return false
        }
        return true
    }

    /**
     * 返回值处理
     */
    private fun <T> responseCall(
        response: BaseResponse<T>?,
        needLogin: (() -> Unit?)? = null
    ): T? {
        if (response == null) {
            GlobalScope.launch(Dispatchers.Main) {
                ToastUtils.showShort("请求异常")
            }
            return null
        }
        if (response.getErrCode() == "200") {
            return response.getData()
        } else  {
            Log.e("Network", "post fail" + response.getErrMsg())
            GlobalScope.launch(Dispatchers.Main) {
                ToastUtils.showShort(response.getErrMsg() ?: "")
            }
            return null
        }
    }

    private fun responseRaw(data : String) : String {
        if (data.length == 16) {
            ToastUtils.showShort("注册成功， 请登录")
        } else {
            ToastUtils.showShort("注册失败：" + data)
        }
        return data
    }

    private fun getDefaultApi(): ApiService {
        return RetrofitClient.getInstance().getDefault(ApiService::class.java)
    }

    private fun createQuestionnaireResult(map: Map<String, String>): List<QuestionnaireResultVO>{
        val questionnaireResultList = mutableListOf<QuestionnaireResultVO>()

        for ((questionContent, label) in map) {
            val questionAvailableResultVO = QuestionAvailableResultVO(
                key = "someKey",
                label = label,
                mbtiIntrovertScore = 0,
                mbtiIntuitionScore = 0,
                mbtiFellingScore = 0,
                mbtiPerceivingScore = 0
            )

            val questionInfoVO = QuestionInfoVO(
                id = 1L,
                templateType = "mbti_quest",
                profileType = 200,
                inputType = 3,
                questionContent = questionContent,
                availableResultVOList = listOf(questionAvailableResultVO)
            )

            val questionnaireResultVO = QuestionnaireResultVO(
                questionInfo = questionInfoVO,
                questionResult = questionAvailableResultVO
            )
            questionnaireResultList.add(questionnaireResultVO)
        }
        return questionnaireResultList
    }

}
