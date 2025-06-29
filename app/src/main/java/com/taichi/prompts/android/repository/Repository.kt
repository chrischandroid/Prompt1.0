package com.taichi.prompts.android.repository

import android.util.Log
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.android.repository.data.MbtiResultVO
import com.taichi.prompts.android.repository.data.QuestionAvailableResultVO
import com.taichi.prompts.android.repository.data.QuestionConfigVO
import com.taichi.prompts.android.repository.data.QuestionnaireResultVO
import com.taichi.prompts.android.repository.data.RegisterRequest
import com.taichi.prompts.android.repository.data.SeedFromUserVO
import com.taichi.prompts.android.repository.data.UserAdmireRequest
import com.taichi.prompts.android.repository.data.UserBaseInfoRequest
import com.taichi.prompts.android.repository.data.UserBaseVO
import com.taichi.prompts.android.repository.data.UserLoginRequest
import com.taichi.prompts.android.repository.data.UserLoginSmsRequest
import com.taichi.prompts.android.repository.data.UserLoginVO
import com.taichi.prompts.android.repository.data.UserProfileRequest
import com.taichi.prompts.android.repository.data.UserProfileVO
import com.taichi.prompts.android.repository.data.UserRecommendRequest
import com.taichi.prompts.android.repository.data.UserRecommendVO
import com.taichi.prompts.android.repository.data.UserRegisterDTO
import com.taichi.prompts.android.repository.data.UserSeedVO
import com.taichi.prompts.android.repository.data.UserSimpleInfoRequest
import com.taichi.prompts.android.repository.data.UserSocialLikeRequest
import com.taichi.prompts.http.BaseMatchResponse
import com.taichi.prompts.http.BaseQuestionResponse
import com.taichi.prompts.http.BaseResponse
import com.taichi.prompts.http.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.File

object Repository {
    private const val Success_Code = 0
    private const val Need_login_Code = -1001
    private const val registerType_name_password = 3

    suspend fun getHomeList(token: String, type : Int): UserRecommendVO? {
        val userProfileMatchRequest = UserRecommendRequest(
            1, mutableMapOf(
                "ageMin" to "18",
                "ageMax" to "80"
            ), 0, 20
        )
        val data: BaseResponse<UserRecommendVO>? = getDefaultApi().homeList(userProfileMatchRequest, token)
        return responseCall(data)
    }

    suspend fun getSeedList(seedType : Int, token: String): List<UserSeedVO>? {
        val data: BaseResponse<List<UserSeedVO>>? = getDefaultApi().getSeedList(seedType, token)
        return responseCall(data)
    }

    suspend fun getUserDetail(token: String, id: String): UserBaseVO? {
        val data: BaseResponse<UserBaseVO>? = getDefaultApi().getUserDetail(id, token)
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
     * 发短信
     */
    suspend fun sendMessage(region: String, number : String): String {
        val dataList = UserLoginSmsRequest(region, number)
        val data: BaseResponse<String> = getDefaultApi().sendMessage(dataList)
        return responseCall(data).toString()
    }

    /**
     * 短信登录
     */
    suspend fun loginMessage(number: String, code : String): UserLoginVO? {
        val dataList = UserLoginRequest(number, code, 2)
        val data: Response<BaseResponse<UserLoginVO?>> = getDefaultApi().loginMessage(dataList)
        return responseWithHeaderCall(data)
    }

    suspend fun updateImg(filePath : String, token : String) : String {
        val file = File(filePath)
        val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
        val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)
        val fileTypeBody = RequestBody.create(MediaType.parse("text/plain"), "1")
        val data: BaseResponse<String> = getDefaultApi().updateImg(token, filePart, fileTypeBody)
        return responseCall(data).toString()
    }

    suspend fun loginWithToken(number: String, token : String): UserLoginVO? {
        val dataList = UserLoginRequest(number, "", 2)
        val data: Response<BaseResponse<UserLoginVO?>> = getDefaultApi().loginWithToken(dataList, token)
        return responseWithHeaderCall(data)
    }

    suspend fun admire(userAdmireRequest: UserSocialLikeRequest, token: String) : Boolean? {
        return try {
            val data: BaseResponse<Boolean> = getDefaultApi().admire(userAdmireRequest, token)
            responseCall(data)
        } catch (e: HttpException) {
            Log.e("AdmireError", "HTTP error: ${e.message}")
            GlobalScope.launch(Dispatchers.Main) {
                ToastUtils.showShort("服务器内部错误，请稍后重试")
            }
            null
        }
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
     * 更新简易信息
     */
    suspend fun updateSimpleProfile(info : UserSimpleInfoRequest, token: String): UserBaseVO? {
        val data: BaseResponse<UserBaseVO> = getDefaultApi().updateSimpleInfo(info, token)
        return responseCall(data)
    }

    /**
     * 更新信息
     */
    suspend fun updateProfile(info : UserBaseInfoRequest, token: String): String {
        val data: BaseResponse<String> = getDefaultApi().updateInfo(info, token)
        return responseCall(data).toString()
    }

    /**
     * 获取SigId
     */
    suspend fun getUserSig(token : String) : String {
        try {
            val data: BaseResponse<String> = getDefaultApi().getUserSig(token)
            return responseCall(data).toString()
        } catch (e : HttpException) {
            return ""
        }
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


    private fun <T> responseWithHeaderCall(
        response: Response<BaseResponse<T?>>,
        needLogin: (() -> Unit?)? = null
    ): T? {
        if (response == null || response.body() == null) {
            GlobalScope.launch(Dispatchers.Main) {
                ToastUtils.showShort("请求异常")
            }
            return null
        }
        val authorizationToken = response.headers().get("authorization_token")
        if (authorizationToken != null) {
            SPUtils.getInstance().put(Constants.SP_USER_TOKEN, authorizationToken)
        } else {
            Log.w("AuthorizationToken", "Authorization token was not found in the response headers")
        }
        val responseBody = response.body()
        return responseCall(responseBody)
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
            Log.e("Network", "postfail--" + response.getErrMsg())
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

            val questionInfoVO = QuestionConfigVO(
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

    private fun createMbtiQuestionnaireResult(map : MutableMap<String, Pair<String, QuestionConfigVO>>): List<QuestionnaireResultVO>{
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

                val questionInfoVO = QuestionConfigVO(
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
    suspend fun getQuestionList(id : String, i: Int) : List<QuestionConfigVO>? {
        val data: BaseQuestionResponse<List<QuestionConfigVO>>? =
            getDefaultApi().getQuestionList(id, "mbti_quest", i) as BaseQuestionResponse<List<QuestionConfigVO>>?
        return responseQuestionCall(data)
    }

    suspend fun getQuestion(token : String, id : String, i: Int) : List<QuestionConfigVO>? {
        val data: BaseResponse<List<QuestionConfigVO>?> =
            getDefaultApi().getQuestionList(token, id, i)
        return responseCall(data)
    }



    /**
     * 保存prompt
     */
    suspend fun saveMbtiQuestion(id: String, map: MutableMap<String, Pair<String, QuestionConfigVO>>): UserProfileVO? {
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

    suspend fun getSeedFromUser(id: String, i: Long) : SeedFromUserVO? {
        val data : BaseResponse<SeedFromUserVO?> = getDefaultApi().getSeedFromUser(id, i)
        return responseCall(data)
    }
}
