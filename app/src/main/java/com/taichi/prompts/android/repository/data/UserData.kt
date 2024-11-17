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

data class RegisterRequest(
    val userRegisterDTO: UserRegisterDTO
)

data class UserProfileMatchRequest(
    val userId : String,
    val profileType : Int,
    val offset : Long,
    val pageSize : Long
)

data class UpdateInfoRequest(
    val userBaseDTO: UserBaseDTO
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

data class QuestionInfoVO (
    val id : Long,
    val templateType : String,
    val profileType : Int,
    val inputType : Int,
    val questionContent : String,
    val availableResultVOList : List<QuestionAvailableResultVO>
)

data class QuestionAvailableResultVO(
    val key : String,
    val label : String,
    val mbtiIntrovertScore : Int,
    val mbtiIntuitionScore : Int,
    val mbtiFellingScore : Int,
    val mbtiPerceivingScore : Int
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