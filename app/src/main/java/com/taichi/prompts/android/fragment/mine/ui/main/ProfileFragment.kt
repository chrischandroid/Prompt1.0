package com.taichi.prompts.android.fragment.mine.ui.main

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import android.graphics.Color
import com.taichi.prompts.android.R
import com.taichi.prompts.android.BR
import com.taichi.prompts.android.databinding.FragmentProfileviewBinding
import com.taichi.prompts.android.fragment.mine.ProfileViewModel
import com.taichi.prompts.android.repository.Repository.updateProfile
import com.taichi.prompts.base.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileviewBinding, ProfileViewModel>(){
    override fun getLayoutId(): Int {
        return R.layout.fragment_profileview
    }

    override fun getViewModelId(): Int {
        return BR.profileVm
    }

    override fun initViewData() {
        initClick()
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }

    fun initClick() {
        val linearLayout = view?.findViewById<LinearLayout>(R.id.gallery1)
        linearLayout?.viewTreeObserver?.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                linearLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)

                val totalWidth = linearLayout.width
                val availableWidth = totalWidth - 30
                val relativeLayoutWidth = availableWidth / 3

                val relativeLayout1 = view?.findViewById<RelativeLayout>(R.id.photo1)
                val relativeLayout2 = view?.findViewById<RelativeLayout>(R.id.photo2)
                val relativeLayout3 = view?.findViewById<RelativeLayout>(R.id.photo3)

                val layoutParams = LinearLayout.LayoutParams(relativeLayoutWidth, relativeLayoutWidth)
                relativeLayout1?.layoutParams = layoutParams
                relativeLayout2?.layoutParams = layoutParams
                relativeLayout3?.layoutParams = layoutParams

                val relativeLayout4 = view?.findViewById<RelativeLayout>(R.id.photo4)
                val relativeLayout5 = view?.findViewById<RelativeLayout>(R.id.photo5)
                val relativeLayout6 = view?.findViewById<RelativeLayout>(R.id.photo6)
                relativeLayout4?.layoutParams = layoutParams
                relativeLayout5?.layoutParams = layoutParams
                relativeLayout6?.layoutParams = layoutParams

                relativeLayout1?.requestLayout()
                relativeLayout2?.requestLayout()
                relativeLayout3?.requestLayout()
            }
        })

        val text1 = view?.findViewById<TextView>(R.id.spanner1)
        val originalText = "封面照 *"
        val spannableString = SpannableString(originalText)
        val asteriskIndex = originalText.indexOf("*")
        if (asteriskIndex != -1) {
            spannableString.setSpan(
                ForegroundColorSpan(Color.RED),
                asteriskIndex,
                asteriskIndex + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        text1?.text = spannableString

        val text2 = view?.findViewById<TextView>(R.id.spanner2)
        val originalText1 = "我觉得 *"
        val spannableString1 = SpannableString(originalText1)
        val asteriskIndex1 = originalText1.indexOf("*")
        if (asteriskIndex1 != -1) {
            spannableString1.setSpan(
                ForegroundColorSpan(Color.RED),
                asteriskIndex1,
                asteriskIndex1 + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        text2?.text = spannableString1



    }

}



