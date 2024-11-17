package com.taichi.prompts.http

import java.io.Serializable

class BaseResponse<T> : Serializable {
    //private var errorMsg: String? = null
    private var code : String = "0"
    //errorCode = -1001 代表登录失效，需要重新登录。
    //private var errorCode: Int? = null
    private var msg : String = "null"
    private var data: T? = null

    fun getErrMsg(): String? {
        return msg
    }

    fun setErrMsg(message: String?) {
        this.msg = message.toString()
    }

    fun getData(): T? {
        return data
    }

    fun setData(data: T) {
        this.data = data
    }

    fun getErrCode(): String? {
        return code
    }

    fun setErrCode(errCode: String?) {
        this.code = errCode.toString()
    }
}

class BaseMatchResponse<T> : Serializable {
    //private var errorMsg: String? = null
    private var code : String = "0"
    //errorCode = -1001 代表登录失效，需要重新登录。
    //private var errorCode: Int? = null
    private var msg : String = "null"
    private var profileType : Int = 0
    private var userProfileMatchVOList: T? = null

    fun getErrMsg(): String? {
        return msg
    }

    fun setErrMsg(message: String?) {
        this.msg = message.toString()
    }

    fun getData(): T? {
        return userProfileMatchVOList
    }

    fun setData(data: T) {
        this.userProfileMatchVOList = data
    }

    fun getErrCode(): String? {
        return code
    }

    fun setErrCode(errCode: String?) {
        this.code = errCode.toString()
    }
}

