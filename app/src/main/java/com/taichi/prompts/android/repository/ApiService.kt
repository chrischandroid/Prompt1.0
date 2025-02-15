package com.taichi.prompts.android.repository
import com.google.gson.stream.JsonToken
import com.taichi.prompts.android.repository.data.MbtiResultVO
import com.taichi.prompts.android.repository.data.QuestionInfoVO
import com.taichi.prompts.android.repository.data.RegisterRequest
import com.taichi.prompts.android.repository.data.UpdateInfoRequest
import com.taichi.prompts.android.repository.data.UserBaseInfoRequest
import com.taichi.prompts.android.repository.data.UserBaseVO
import com.taichi.prompts.android.repository.data.UserData
import com.taichi.prompts.android.repository.data.UserLoginRequest
import com.taichi.prompts.android.repository.data.UserLoginResponse
import com.taichi.prompts.android.repository.data.UserLoginSmsRequest
import com.taichi.prompts.android.repository.data.UserProfileMatchVOList
import com.taichi.prompts.android.repository.data.UserProfileRequest
import com.taichi.prompts.android.repository.data.UserProfileVO
import com.taichi.prompts.android.repository.data.UserRecVO
import com.taichi.prompts.android.repository.data.UserRecommendRequest
import com.taichi.prompts.android.repository.data.UserRegisterDTO
import com.taichi.prompts.android.repository.data.UserSimpleInfoRequest
import com.taichi.prompts.http.ApiAddress
import com.taichi.prompts.http.BaseMatchResponse
import com.taichi.prompts.http.BaseQuestionResponse
import com.taichi.prompts.http.BaseResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    /**
     * 获取首页列表数据
     */
    @POST(ApiAddress.Match_List)
    suspend fun homeList(
        @Body userProfileMatchRequest: UserRecommendRequest,
        @Header("authorization_token") token: String,
    ): BaseMatchResponse<List<UserRecVO>>?

    /**
     * 获取问题列表
     */
    @GET(ApiAddress.Question_List)
    suspend fun getQuestionList(
        @Query("userId") userId: String,
        @Query("templateType") param2: String,
        @Query("profileType") param3: Int
    ): BaseQuestionResponse<List<QuestionInfoVO>>?


    /**
     * 登录
     */
    @POST(ApiAddress.Login)
    suspend fun login(
        @Body registerRequest: RegisterRequest,
        @Header("User-Agent") userAgent: String = "Apifox/1.0.0 (https://apifox.com)",
        @Header("Content-Type") contentType: String = "application/json"
    ): BaseResponse<String>

    /**
     * 短信
     */
    @POST(ApiAddress.SendMsg)
    suspend fun sendMessage(
        @Body userLoginSmsRequest: UserLoginSmsRequest,
        @Header("User-Agent") userAgent: String = "Apifox/1.0.0 (https://apifox.com)",
        @Header("Content-Type") contentType: String = "application/json",
    ): BaseResponse<String>

    /**
     * 短信登录
     */
    @POST(ApiAddress.LoginMsg)
    suspend fun loginMessage(
        @Body userLoginRequest: UserLoginRequest,
        @Header("User-Agent") userAgent: String = "Apifox/1.0.0 (https://apifox.com)",
        @Header("Content-Type") contentType: String = "application/json",
    ): Response<BaseResponse<UserLoginResponse?>>

    /**
     * 免密登录
     */
    @POST(ApiAddress.LoginMsg)
    suspend fun loginWithToken(
        @Body userLoginRequest: UserLoginRequest,
        @Header("authorization_token") token: String,
        @Header("User-Agent") userAgent: String = "Apifox/1.0.0 (https://apifox.com)",
        @Header("Content-Type") contentType: String = "application/json",
    ): Response<BaseResponse<UserLoginResponse?>>

    /**
     * 获取mbti
     */
    @GET(ApiAddress.GetMBTI)
    suspend fun getMBTI(
        @Query("userId") id : String,
        @Header("User-Agent") userAgent: String = "Apifox/1.0.0 (https://apifox.com)",
        @Header("Content-Type") contentType: String = "application/json"
    ): BaseResponse<MbtiResultVO?>?

    /**
     * 注册
     */
    @POST(ApiAddress.Register)
    suspend fun register(
        @Body registerRequest: RegisterRequest,
        @Header("User-Agent") userAgent: String = "Apifox/1.0.0 (https://apifox.com)",
        @Header("Content-Type") contentType: String = "application/json"
    ): BaseResponse<String>

    /**
     * 更新信息
     */
    @POST(ApiAddress.UpdateInfo)
    suspend fun updateInfo(
        @Body request: UserBaseInfoRequest,
        @Header("authorization_token") token: String,
        @Header("User-Agent") userAgent: String = "Apifox/1.0.0 (https://apifox.com)",
        @Header("Content-Type") contentType: String = "application/json"
    ): BaseResponse<String>

    /**
     * 更新简易信息
     */
    @POST(ApiAddress.SaveSimpleInfo)
    suspend fun updateSimpleInfo(
        @Body request: UserSimpleInfoRequest,
        @Header("authorization_token") token: String,
        @Header("User-Agent") userAgent: String = "Apifox/1.0.0 (https://apifox.com)",
        @Header("Content-Type") contentType: String = "application/json"
    ): BaseResponse<UserBaseVO>


    /**
     * 获取UserSig
     */
    @GET(ApiAddress.GetUserSig)
    suspend fun getUserSig(
        @Query("userId") id : String,
        @Header("User-Agent") userAgent: String = "Apifox/1.0.0 (https://apifox.com)",
        @Header("Content-Type") contentType: String = "application/json"
    ): BaseResponse<String>

    /**
     * 更新Prompt
     */
    @POST(ApiAddress.UpdatePrompt)
    suspend fun updatePrompt(
        @Body request: UserProfileRequest,
        @Header("User-Agent") userAgent: String = "Apifox/1.0.0 (https://apifox.com)",
        @Header("Content-Type") contentType: String = "application/json"
    ): BaseResponse<UserProfileVO?>?

    /**
     * 登出
     */
    @GET(ApiAddress.Logout)
    suspend fun logout(): BaseResponse<Any>?

    /**
     * post body
     */
    @POST("")
    suspend fun loginBody(@Body requestBody: RequestBody): BaseResponse<Any>
}


