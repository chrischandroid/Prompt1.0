package com.taichi.prompts.android.activity.home

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.SPUtils
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.android.common.Constants.SP_USER_BIRTH
import com.taichi.prompts.android.common.Constants.SP_USER_GENDER
import com.taichi.prompts.android.common.Constants.SP_USER_ID
import com.taichi.prompts.android.common.Constants.SP_USER_NICKNAME
import com.taichi.prompts.android.repository.Repository
import com.taichi.prompts.android.repository.data.UserBaseDTO
import com.taichi.prompts.base.BaseViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle
import java.util.Locale

class TabViewModel(application: Application) : BaseViewModel(application) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateProfile() {
        val name = SPUtils.getInstance().getString(SP_USER_NICKNAME)
        val birth = SPUtils.getInstance().getString(SP_USER_BIRTH)

        val currentYear = LocalDate.now().year
        val birthYear = birth.substring(0, 4).toInt()
        val age = currentYear - birthYear
        val marryNum: Int = 0
        val id = SPUtils.getInstance().getString(SP_USER_ID)
        val genderNum: Int = SPUtils.getInstance().getInt(SP_USER_GENDER)
        val cityName: String = "null"

        val degreeNum: Int = 0
        val hometownStr: String = "null"
        val phoneStr: String = "null"
        val mailStr: String = "null"
        val info = UserBaseDTO(
            id,
            name,
            age,
            birth,
            marryNum,
            genderNum,
            cityName,
            degreeNum,
            hometownStr,
            phoneStr,
            mailStr
        )
        val token = SPUtils.getInstance().getString(Constants.SP_USER_TOKEN)
        viewModelScope.launch {
            val data: String = Repository.updateProfile(info, token)
        }
    }
}
