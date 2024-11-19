package com.taichi.prompts.android.repository.data
import com.taichi.prompts.android.repository.data.UserData

data class UserProfileMatchVOList(
    val score: Double?,
    val userBaseVO: UserBaseVO?,
    val userProfileVO : UserProfileVO
)