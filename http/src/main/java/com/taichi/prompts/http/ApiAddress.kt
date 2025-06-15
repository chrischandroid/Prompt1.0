package com.taichi.prompts.http

object ApiAddress {

    //首页
    const val Match_List = "/userIndex/recommend"
    const val Seed_List = "/user/social/getSeedList"
    //问题列表
    const val Question_List = "/lingqi/config/getQuestionList"

    //登录
    const val Login = "/userInfo/login"

    const val User_Detail = "/userIndex/getRecommendUserDetail"
    const val updateImg = "/userInfo/cos/uploadImg"
    //注册
    const val Register = "/userInfo/register"
    //注册
    const val SendMsg = "/userRegister/sendMsg"

    //点赞
    const val admire = "/user/social/admire"

    //短信登录
    const val LoginMsg = "/userRegister/login"

    //更新信息
    const val UpdateInfo = "/userInfo/userBaseInfoSave"

    //更新简易信息
    const val SaveSimpleInfo = "/userInfo/saveSimpleInfo"

    //更新prompt
    const val UpdatePrompt = "/userProfile/questionnaire/saveProfile"

    //获取UserSig
    const val GetUserSig = "/user/im/userSig/get"

    //登出
    const val Logout = "user/logout/json"

    //获取MBTI
    const val GetMBTI = "/userProfile/getUserMBTI"


}
