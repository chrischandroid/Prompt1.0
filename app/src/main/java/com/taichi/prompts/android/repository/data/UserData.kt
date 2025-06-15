package com.taichi.prompts.android.repository.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import org.w3c.dom.Comment


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

data class UserRecommendVO(
    val userRecVOList : List<UserRecVO>,
    val refreshTime : Long
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
    val showImgUrlList : List<String>,
    val promptQuestion : String,
    val promptAnswer : String
)

data class UserRecommendRequest (
    val recommendType : Int,
    val filterParams : Map<String, String>,
    val offset : Int,
    val pageSize : Int
)

data class UpdateInfoRequest(
    val userBaseDTO: UserBaseVO
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
    val questionInfo : QuestionConfigVO,
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

@Parcelize
data class QuestionConfigVO (
    val id : Long,
    val templateType : String,
    val profileType : Int,
    val inputType : Int,
    val questionContent : String,
    val availableResultVOList : List<QuestionAvailableResultVO>
) : Parcelable

data class UserQuestionAnswerVO (
    val key : String,
    val label : String,
    val MBTI_introvert_score : Int,
    val MBTI_intuition_score : Int,
    val MBTI_feeling_score : Int,
    val MBTI_perceiving_score : Int
)

@Parcelize
data class QuestionAvailableResultVO(
    val key : String,
    val label : String,
    val MBTI_introvert_score : Int,
    val MBTI_intuition_score : Int,
    val MBTI_feeling_score : Int,
    val MBTI_perceiving_score : Int
) : Parcelable

data class  MbtiResultVO(
    val mbtiType : String
)

data class UserBaseVO(
    val userId : String,
    val userNickName: String,
    val headImgUrl: String,
    val introduction: String,
    val personalTags: String,
    val age : Int,
    val birthDay : String,
    val marriageStatus : Int,
    val gender : Int,
    val city : String,
    val degree : Int,
    val hometown : String,
    val phoneNo: String,
    val mailAddress: String,
    val showImgUrlList : List<String>,
    val school: String,
    val height : Int,
    val weight : Int,
    val career : String,
    val mbti : String,
    val asset : String
)

data class UserBaseInfoRequest(
    val userId : String,
    val userNickName: String,
    val headImgUrl: String,
    val age : Int,
    val birthDay : String,
    val marriageStatus : Int,
    val gender : Int,
    val city : String,
    val degree : Int,
    val hometown : String,
    val phoneNo: String,
    val mailAddress: String,
    val showImgUrlList : List<String>,
    val school: String,
    val height : Int,
    val weight : Int,
    val career : String,
    val introduction : String,
    val personalTags : String,
    val mbti : String,
    val asset : String
)

data class UserLoginVO(
    val userBaseVO : UserBaseVO
)

data class UserProfileVO(
    val templateType : String,
    val profileType : Int,
    val questionnaireMap : Map<String, String>,
    val profileTag : String
)
data class UserAdmireRequest(
    val userId : String,
    val admireUserId : String,
    val admireType : Int,
    val admireFlag : Boolean
)

data class UserSocialLikeRequest(
    val admireModule : Int,
    val admireType : Int,
    val oppositeUserId : String,
    val comment: String
)

data class UserSeedVO(
    val seedId : Long,
    val headImgUrl: String,
    val nickName : String,
    val oppositeUserId: String,
    val age: Int,
    val admireOptType : Int,
    val seedEndTime : Long,
    val seedStatus : Int
)