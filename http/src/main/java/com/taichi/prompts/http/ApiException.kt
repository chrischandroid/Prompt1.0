package com.taichi.prompts.http



class ApiException : Throwable {

    //这些字段根据后台定义去修改
    private var code = 0
    private var displayMessage: String? = null


    constructor(code: Int, displayMessage: String?) {
        this.code = code
        this.displayMessage = displayMessage
    }

    constructor(code: Int, message: String?, displayMessage: String?) : super(message) {
        this.code = code
        this.displayMessage = displayMessage
    }

    fun getCode(): Int {
        return code
    }

    fun setCode(code: Int) {
        this.code = code
    }

    fun getDisplayMessage(): String? {
        return displayMessage
    }

    fun setDisplayMessage(displayMessage: String?) {
        this.displayMessage = displayMessage
    }

    fun getUMessage(): String? {
        return displayMessage
    }
}

