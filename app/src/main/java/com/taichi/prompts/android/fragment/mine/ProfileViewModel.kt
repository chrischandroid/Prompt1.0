package com.taichi.prompts.android.fragment.mine

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.SPUtils
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.android.repository.Repository
import com.taichi.prompts.android.repository.data.UserBaseDTO
import com.taichi.prompts.base.BaseViewModel
import com.taichi.prompts.base.SingleLiveEvent
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : BaseViewModel(application) {
    val userId : String = "0"
    val name = ObservableField<String>()
    val age = ObservableField<String>()
    val marriage = ObservableField<String>()
    val gender = ObservableField<String>()
    val birthYear = ObservableField<String>()
    val birthMonth = ObservableField<String>()
    val birthDay = ObservableField<String>()
    val city = ObservableField<String>()
    val degree = ObservableField<String>()
    val hometown = ObservableField<String>()
    val phone = ObservableField<String>()
    val mail = ObservableField<String>()

    fun updateProfile() {
        val id = SPUtils.getInstance().getString(Constants.SP_USER_ID)
        val nickName : String = name?.get().toString() ?: "default"
        val ageNum : Int = age?.get().toString().toIntOrNull() ?: 0

        val y = birthYear?.get().toString() ?:"0000"
        val m = birthMonth?.get().toString() ?: "00"
        val d = birthDay?.get().toString() ?: "00"
        val formattedDate = "$y-${m.padStart(2, '0')}-${d.padStart(2, '0')}"

        val mStr : String? = marriage?.get().toString()
        val stringToIntMap: Map<String, Int> = mapOf(
            "未婚" to 0,
            "已婚" to 1,
            "离婚" to 2,
            "离婚带娃" to 3
        )
        val marryNum: Int = mStr?.let {
            if (stringToIntMap.containsKey(it)) {
                stringToIntMap[it] ?: throw NoSuchElementException("Mapping exists but value is null, which shouldn't happen with the given map")
            } else {
                0
            }
        } ?: 0

        val gStr : String? = gender?.get().toString()
        val genderMap: Map<String, Int> = mapOf(
            "未知" to 0,
            "男性" to 1,
            "女性" to 2
        )
        val genderNum: Int = gStr?.let {
            if (genderMap.containsKey(it)) {
                genderMap[it] ?: throw NoSuchElementException("Mapping exists but value is null, which shouldn't happen with the given map")
            } else {
                0
            }
        } ?: 0
        val cityName : String = city?.get().toString() ?: "null"

        val dStr : String? = degree?.get().toString()
        val degreeMap: Map<String, Int> = mapOf(
            "未知" to 0,
            "小学" to 1,
            "初中" to 2,
            "高中" to 3,
            "本科" to 4,
            "硕士" to 5,
            "博士" to 6,
        )
        val degreeNum: Int = dStr?.let {
            if (degreeMap.containsKey(it)) {
                degreeMap[it] ?: throw NoSuchElementException("Mapping exists but value is null, which shouldn't happen with the given map")
            } else {
                0
            }
        } ?: 0
        val hometownStr : String = hometown?.get().toString() ?: "null"
        val phoneStr : String = phone?.get().toString() ?: "null"
        val mailStr : String = mail?.get().toString() ?: "null"
        val info = UserBaseDTO(id, nickName, ageNum, formattedDate, marryNum, genderNum, cityName, degreeNum, hometownStr, phoneStr, mailStr)
        val token = SPUtils.getInstance().getString(Constants.SP_USER_TOKEN)
        viewModelScope.launch {
            val data: String = Repository.updateProfile(info, token)
        }
    }

}
