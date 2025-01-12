package com.taichi.prompts.android.repository.data

import kotlinx.serialization.Serializable



/**
 * 注册或者登录后返回的用户数据
 */
data class UserData(
    //是否为管理员
    val admin: Boolean?,
    val chapterTops: List<Any?>?,
    val coinCount: Int?,
    val collectIds: List<Any?>?,
    val email: String?,
    val icon: String?,
    val id: Int?,
    val nickname: String?,
    val password: String?,
    val publicName: String?,
    val token: String?,
    val type: Int?,
    val username: String?
)

data class RegisterData(
    val userId : Int?
)

data class UserRegisterDTO(
    val registerType: Int,
    val registerAccount: String,
    val registerSecret: String
)

data class UserLoginSmsRequest(
    val regionNo: String,
    val phoneNo: String
)

data class UserLoginRequest(
    val registerAccount: String,
    val registerSecret: String,
    val registerType: Int
)

data class RegisterRequest(
    val userRegisterDTO: UserRegisterDTO
)

data class UserRecVO(
    val userId : String,
    val userNickName: String,
    val headImgUrl : String,
    val age : Int,
    val mbtiTag : String,
    val birthDay : String,
    val gender : Int,
    val height: String,
    val weight: String,
    val liveCity : String,
    val hometown : String,
    val college : String,
    val showImgUrlList : List<String>
)

data class UserRecommendRequest (
    val recommendType : Int,
    val filterParams : Map<String, String>,
    val offset : Int,
    val pageSize : Int
)

data class UpdateInfoRequest(
    val userBaseDTO: UserBaseDTO
)

data class  UserSimpleInfoRequest(
    val userNickName: String,
    val headImgUrl: String,
    val age: Int,
    val gender: Int,
    val phoneNo: String,
    val templateType: String,
    val profileType: Int,
    val userQuestionnaireVOList: List<UserQuestionnaireVO>
)

data class UserProfileRequest(
    val userId : String,
    val templateType : String,
    val profileType : Int,
    val questionnaireResult : List<QuestionnaireResultVO>?,
    val questionnaireSnapshot : List<String>?
)

data class QuestionnaireResultVO (
    val questionInfo : QuestionInfoVO,
    val questionResult : QuestionAvailableResultVO
)

data class UserQuestionnaireVO(
    val userQuestionInfoVO: UserQuestionInfoVO,
    val userQuestionAnswerVO : UserQuestionAnswerVO
)


data class UserQuestionInfoVO (
    val id : Long,
    val questionContent : String
)

data class QuestionInfoVO (
    val id : Long,
    val templateType : String,
    val profileType : Int,
    val inputType : Int,
    val questionContent : String,
    val availableResultVOList : List<QuestionAvailableResultVO>
)

data class UserQuestionAnswerVO (
    val answerKey : String,
    val answerContent : String,
    val MBTI_introvert_score : Int,
    val MBTI_intuition_score : Int,
    val MBTI_feeling_score : Int,
    val MBTI_perceiving_score : Int
)

data class QuestionAvailableResultVO(
    val key : String,
    val label : String,
    val MBTI_introvert_score : Int,
    val MBTI_intuition_score : Int,
    val MBTI_feeling_score : Int,
    val MBTI_perceiving_score : Int
)
data class  MbtiResultVO(
    val mbtiType : String
)

data class UserBaseDTO(
    val userId : String,
    val userNickName: String,
    val age : Int,
    val birthDay : String,
    val marriageStatus : Int,
    val gender : Int,
    val city : String,
    val degree : Int,
    val hometown : String,
    val phoneNo: String,
    val mailAddress: String
)

data class UserLoginResponse(
    val userBaseVO : UserBaseDTO
)

data class UserBaseVO(
    val userId : String,
    val userNickName: String,
    val headImgUrl : String,
    val introduction : String,
    val age : Int,
    val birthDay : String,
    val marriageStatus : Int,
    val gender : Int,
    val city : String,
    val degree : Int,
    val hometown : String,
    val phoneNo: String,
    val mailAddress: String,
    val showImgUrlList : List<String>
)

data class UserProfileVO(
    val templateType : String,
    val profileType : Int,
    val questionnaireMap : Map<String, String>,
    val profileTag : String
)