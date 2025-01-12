package com.taichi.prompts.http

object ApiAddress {

    //首页
    const val Match_List = "/userIndex/recommend"

    //问题列表
    const val Question_List = "userProfile/questionnaire/getQuestionList"

    //登录
    const val Login = "/userInfo/login"

    //注册
    const val Register = "/userInfo/register"

    //注册
    const val SendMsg = "/userRegister/sendMsg"

    //短信登录
    const val LoginMsg = "/userRegister/login"

    //更新信息
    const val UpdateInfo = "/userInfo/save"

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
