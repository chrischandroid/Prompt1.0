package com.taichi.prompts.http

object BaseUrlConstants {
    private const val baseUrl: String = "http://124.223.48.116:8080"

    fun getHost(): String {
        return baseUrl
    }

}
