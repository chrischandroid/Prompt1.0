package com.taichi.prompts.android.repository

import android.util.Log
import com.blankj.utilcode.util.ToastUtils
import com.taichi.prompts.android.repository.data.MbtiResultVO
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
import com.taichi.prompts.http.BaseMatchResponse
import com.taichi.prompts.http.BaseQuestionResponse
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
            id, type, 0, 20
        )
        val data: BaseMatchResponse<List<UserProfileMatchVOList>>? = getDefaultApi().homeList(userProfileMatchRequest)
        return responseMatchCall(data)
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

    suspend fun getUserMBTI(id: String) : MbtiResultVO? {
        val data: BaseResponse<MbtiResultVO?>? = getDefaultApi().getMBTI(id)
        return responseCall(data)
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
    suspend fun saveQuestion(id: String, map: MutableMap<String, Pair<String, Long>>): UserProfileVO? {
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

    private fun <T> responseMatchCall(
        response: BaseMatchResponse<T>?,
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

    private fun <T> responseQuestionCall(
        response: BaseQuestionResponse<T>?,
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

    private fun createQuestionnaireResult(map : MutableMap<String, Pair<String, Long>>): List<QuestionnaireResultVO>{
        val questionnaireResultList = mutableListOf<QuestionnaireResultVO>()

        for ((questionContent, pair) in map)  {
            val (label, id) = pair
            val questionAvailableResultVO = QuestionAvailableResultVO(
                key = "",
                label = label,
                MBTI_introvert_score = 0,
                MBTI_intuition_score = 0,
                MBTI_feeling_score = 0,
                MBTI_perceiving_score = 0
            )

            val questionInfoVO = QuestionInfoVO(
                id = id,
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

    private fun createMbtiQuestionnaireResult(map : MutableMap<String, Pair<String, QuestionInfoVO>>): List<QuestionnaireResultVO>{
        val questionnaireResultList = mutableListOf<QuestionnaireResultVO>()

        for ((questionContent, pair) in map) {
            val (l, answer) = pair
            val result: QuestionAvailableResultVO? = answer.availableResultVOList.firstOrNull { it.label == l }

            result?.let{
                val questionAvailableResultVO = QuestionAvailableResultVO(
                    key = result.key,
                    label = l,
                    MBTI_introvert_score = result.MBTI_introvert_score,
                    MBTI_intuition_score = result.MBTI_intuition_score,
                    MBTI_feeling_score = result.MBTI_feeling_score,
                    MBTI_perceiving_score = result.MBTI_perceiving_score
                )

                val questionInfoVO = QuestionInfoVO(
                    id = answer.id,
                    templateType = "mbti_quest",
                    profileType = 300,
                    inputType = 2,
                    questionContent = questionContent,
                    availableResultVOList = listOf(questionAvailableResultVO)
                )

                val questionnaireResultVO = QuestionnaireResultVO(
                    questionInfo = questionInfoVO,
                    questionResult = questionAvailableResultVO
                )
                questionnaireResultList.add(questionnaireResultVO)
            }

        }
        return questionnaireResultList
    }

    /**
     * 获取SigId
     */
    suspend fun getQuestionList(id : String, i: Int) : List<QuestionInfoVO>? {
        val data: BaseQuestionResponse<List<QuestionInfoVO>>? =
            getDefaultApi().getQuestionList(id, "mbti_quest", i)
        return responseQuestionCall(data)
    }

    /**
     * 保存prompt
     */
    suspend fun saveMbtiQuestion(id: String, map: MutableMap<String, Pair<String, QuestionInfoVO>>): UserProfileVO? {
        val questionnaireResult : List<QuestionnaireResultVO> = createMbtiQuestionnaireResult(map)
        val questionnaireSnapshot : List<String>? = null
        val userProfileRequest = UserProfileRequest(id, "mbti_quest", 300, questionnaireResult, questionnaireSnapshot)
        try {
            val data: BaseResponse<UserProfileVO?>?= getDefaultApi().updatePrompt(userProfileRequest)
            return responseCall(data)
        } catch (e : HttpException) {
            return null
        }
    }

}
