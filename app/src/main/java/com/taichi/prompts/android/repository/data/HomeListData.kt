package com.taichi.prompts.android.repository.data
import com.taichi.prompts.android.repository.data.UserData

data class UserProfileMatchVOList(
    val score: Double?,
    val base: UserBaseVO,
    val profile : UserProfileVO
)

/**
 * 首页
 */
class HomeListData : ArrayList<HomeItem>()

data class HomeItem(
    val showImgUrlList: String?,
    val userNickName: String?,
    val headImgUrl: String?,
    val age: Int?,
    val userId: String?,
    val birthDay: String?,
    val marriageStatus:Int?,
    val gender:Int?,
    val city:String?,
    val degree: Int?,
    val hometown: String?
){
    override fun toString(): String {
        return "HomeItem(showImgUrlList=$showImgUrlList, userNickName=$userNickName, userId=$userId, marriageStatus=$marriageStatus, degree=$degree, hometown=$hometown)"
    }
}
