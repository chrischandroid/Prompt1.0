package com.taichi.prompts.http

object ApiAddress {

    //首页
    const val Match_List = "userProfile/profileMatch"

    //常用网站
    const val Common_Use_Website = "friend/json"

    //搜索热词
    const val Search_Hot_Key = "hotkey/json"

    //知识体系数据
    const val Knowledge_List = "tree/json"

    //知识体系数据明细
    const val Knowledge_List_detail = "article/list/"

    //登录
    const val Login = "/userInfo/login"

    //注册
    const val Register = "/userInfo/register"

    //更新信息
    const val UpdateInfo = "/userInfo/save"

    //更新prompt
    const val UpdatePrompt = "/userProfile/questionnaire/saveProfile"

    //获取UserSig
    const val GetUserSig = "/user/im/userSig/get"

    //登出
    const val Logout = "user/logout/json"

    //收藏文章列表
    const val Collect = "lg/collect/"

    //取消收藏文章列表
    const val Collect_Cancel= "lg/uncollect_originId/"

    //搜索
    const val Search= "article/query/"

    //我的收藏：文章列表
    const val My_Collect= "lg/collect/list/"


    /**
     * 首页文章列表
     */
    fun getArticleList(pageCount: Int): String {
        return "article/list/$pageCount/json"
    }
}
