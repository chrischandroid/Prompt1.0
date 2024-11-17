package com.taichi.prompts.android.fragment.mine

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.SPUtils
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.android.R
import com.taichi.prompts.android.BR
import com.taichi.prompts.android.databinding.ActivityTestBinding
import com.taichi.prompts.android.databinding.ActivityTestBindingImpl
import com.taichi.prompts.android.adapter.Question
import com.taichi.prompts.android.adapter.QuestionAdapter
import com.taichi.prompts.android.fragment.mine.TestViewModel
import com.taichi.prompts.base.BaseActivity

class TestActivity : BaseActivity<ActivityTestBinding, TestViewModel>(){

    val id = SPUtils.getInstance().getString(Constants.SP_USER_ID)
    private var lastClickedTextView: TextView? = null
    private var lastClickedView: View? = null
    private var lastTextColor: Int = 0
    private var lastBackColor: Int = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_test
    }

    override fun getViewModelId(): Int {
        return BR.testVm
    }

    override fun initViewData() {
        initClick()
    }

    private fun initClick() {
        val whiteColor : Int = ContextCompat.getColor(this, R.color.white)
        val grayColor : Int = ContextCompat.getColor(this, R.color.gray)
        val darkGray : Int = ContextCompat.getColor(this, R.color.darkgray)
        var lastgradientDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 50f
            setStroke(5, darkGray)
            setGradientType(GradientDrawable.LINEAR_GRADIENT)
        }
        var currentgradientDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 50f
            setStroke(5, darkGray)
            setGradientType(GradientDrawable.LINEAR_GRADIENT)
        }
        binding?.customButton1?.setOnClickListener{
            val textColor : Int = ContextCompat.getColor(this, R.color.purple_200)
            currentgradientDrawable.setColor(textColor)
            binding?.customButton1?.background = currentgradientDrawable
            binding?.customText1?.setTextColor(whiteColor)
            lastClickedTextView?.setTextColor(lastTextColor)
            lastgradientDrawable.setColor(lastBackColor)
            lastClickedView?.background = lastgradientDrawable
            lastClickedTextView = binding?.customText1
            lastClickedView = binding?.customButton1
            lastTextColor = textColor
            lastBackColor = grayColor
            binding?.mbtiimg?.setImageResource(R.drawable.intj)
            binding?.mbtitext?.setText("未来世界筑梦师")
        }
        binding?.customButton2?.setOnClickListener{
            val textColor : Int = ContextCompat.getColor(this, R.color.purple_200)
            currentgradientDrawable.setColor(textColor)
            binding?.customButton2?.background = currentgradientDrawable
            binding?.customText2?.setTextColor(whiteColor)
            lastClickedTextView?.setTextColor(lastTextColor)
            lastgradientDrawable.setColor(lastBackColor)
            lastClickedView?.background = lastgradientDrawable
            lastClickedTextView = binding?.customText2
            lastClickedView = binding?.customButton2
            lastTextColor = textColor
            lastBackColor = grayColor
            binding?.mbtiimg?.setImageResource(R.drawable.intp)
            binding?.mbtitext?.setText("思维宇宙探险家")
        }
        binding?.customButton3?.setOnClickListener{
            val textColor : Int = ContextCompat.getColor(this, R.color.purple_200)
            currentgradientDrawable.setColor(textColor)
            binding?.customButton3?.background = currentgradientDrawable
            binding?.customText3?.setTextColor(whiteColor)
            lastClickedTextView?.setTextColor(lastTextColor)
            lastgradientDrawable.setColor(lastBackColor)
            lastClickedView?.background = lastgradientDrawable
            lastClickedTextView = binding?.customText3
            lastClickedView = binding?.customButton3
            lastTextColor = textColor
            lastBackColor = grayColor
            binding?.mbtiimg?.setImageResource(R.drawable.entj)
            binding?.mbtitext?.setText("商业帝国霸总范")
        }
        binding?.customButton4?.setOnClickListener{
            val textColor : Int = ContextCompat.getColor(this, R.color.purple_200)
            currentgradientDrawable.setColor(textColor)
            binding?.customButton4?.background = currentgradientDrawable
            binding?.customText4?.setTextColor(whiteColor)
            lastClickedTextView?.setTextColor(lastTextColor)
            lastgradientDrawable.setColor(lastBackColor)
            lastClickedView?.background = lastgradientDrawable
            lastClickedTextView = binding?.customText4
            lastClickedView = binding?.customButton4
            lastTextColor = textColor
            lastBackColor = grayColor
            binding?.mbtiimg?.setImageResource(R.drawable.entp)
            binding?.mbtitext?.setText("辩论场上龙卷风")
        }
        binding?.customButton5?.setOnClickListener{
            val textColor : Int = ContextCompat.getColor(this, R.color.brown_200)
            currentgradientDrawable.setColor(textColor)
            binding?.customButton5?.background = currentgradientDrawable
            binding?.customText5?.setTextColor(whiteColor)
            lastClickedTextView?.setTextColor(lastTextColor)
            lastgradientDrawable.setColor(lastBackColor)
            lastClickedView?.background = lastgradientDrawable
            lastClickedTextView = binding?.customText5
            lastClickedView = binding?.customButton5
            lastTextColor = textColor
            lastBackColor = grayColor
            binding?.mbtiimg?.setImageResource(R.drawable.isfp)
            binding?.mbtitext?.setText("艺术森林小精灵")
        }
        binding?.customButton6?.setOnClickListener{
            val textColor : Int = ContextCompat.getColor(this, R.color.brown_200)
            currentgradientDrawable.setColor(textColor)
            binding?.customButton6?.background = currentgradientDrawable
            binding?.customText6?.setTextColor(whiteColor)
            lastClickedTextView?.setTextColor(lastTextColor)
            lastgradientDrawable.setColor(lastBackColor)
            lastClickedView?.background = lastgradientDrawable
            lastClickedTextView = binding?.customText6
            lastClickedView = binding?.customButton6
            lastTextColor = textColor
            lastBackColor = grayColor
            binding?.mbtiimg?.setImageResource(R.drawable.istp)
            binding?.mbtitext?.setText("冷静拆弹超级侠")
        }
        binding?.customButton7?.setOnClickListener{
            val textColor : Int = ContextCompat.getColor(this, R.color.brown_200)
            currentgradientDrawable.setColor(textColor)
            binding?.customButton7?.background = currentgradientDrawable
            binding?.customText7?.setTextColor(whiteColor)
            lastClickedTextView?.setTextColor(lastTextColor)
            lastgradientDrawable.setColor(lastBackColor)
            lastClickedView?.background = lastgradientDrawable
            lastClickedTextView = binding?.customText7
            lastClickedView = binding?.customButton7
            lastTextColor = textColor
            lastBackColor = grayColor
            binding?.mbtiimg?.setImageResource(R.drawable.esfp)
            binding?.mbtitext?.setText("派对狂欢领舞者")
        }
        binding?.customButton8?.setOnClickListener{
            val textColor : Int = ContextCompat.getColor(this, R.color.brown_200)
            currentgradientDrawable.setColor(textColor)
            binding?.customButton8?.background = currentgradientDrawable
            binding?.customText8?.setTextColor(whiteColor)
            lastClickedTextView?.setTextColor(lastTextColor)
            lastgradientDrawable.setColor(lastBackColor)
            lastClickedView?.background = lastgradientDrawable
            lastClickedTextView = binding?.customText8
            lastClickedView = binding?.customButton8
            lastTextColor = textColor
            lastBackColor = grayColor
            binding?.mbtiimg?.setImageResource(R.drawable.estp)
            binding?.mbtitext?.setText("冒险乐园大玩家")
        }
        binding?.customButton9?.setOnClickListener{
            val textColor : Int = ContextCompat.getColor(this, R.color.blue_200)
            currentgradientDrawable.setColor(textColor)
            binding?.customButton9?.background = currentgradientDrawable
            binding?.customText9?.setTextColor(whiteColor)
            lastClickedTextView?.setTextColor(lastTextColor)
            lastgradientDrawable.setColor(lastBackColor)
            lastClickedView?.background = lastgradientDrawable
            lastClickedTextView = binding?.customText9
            lastClickedView = binding?.customButton9
            lastTextColor = textColor
            lastBackColor = grayColor
            binding?.mbtiimg?.setImageResource(R.drawable.isfj)
            binding?.mbtitext?.setText("温柔守护大天使")
        }
        binding?.customButton10?.setOnClickListener{
            val textColor : Int = ContextCompat.getColor(this, R.color.blue_200)
            currentgradientDrawable.setColor(textColor)
            binding?.customButton10?.background = currentgradientDrawable
            binding?.customText10?.setTextColor(whiteColor)
            lastClickedTextView?.setTextColor(lastTextColor)
            lastgradientDrawable.setColor(lastBackColor)
            lastClickedView?.background = lastgradientDrawable
            lastClickedTextView = binding?.customText10
            lastClickedView = binding?.customButton10
            lastTextColor = textColor
            lastBackColor = grayColor
            binding?.mbtiimg?.setImageResource(R.drawable.istj)
            binding?.mbtitext?.setText("规则帝国铁卫士")
        }
        binding?.customButton11?.setOnClickListener{
            val textColor : Int = ContextCompat.getColor(this, R.color.blue_200)
            currentgradientDrawable.setColor(textColor)
            binding?.customButton11?.background = currentgradientDrawable
            binding?.customText11?.setTextColor(whiteColor)
            lastClickedTextView?.setTextColor(lastTextColor)
            lastgradientDrawable.setColor(lastBackColor)
            lastClickedView?.background = lastgradientDrawable
            lastClickedTextView = binding?.customText11
            lastClickedView = binding?.customButton11
            lastTextColor = textColor
            lastBackColor = grayColor
            binding?.mbtiimg?.setImageResource(R.drawable.estj)
            binding?.mbtitext?.setText("职场风暴领航员")
        }
        binding?.customButton12?.setOnClickListener{
            val textColor : Int = ContextCompat.getColor(this, R.color.blue_200)
            currentgradientDrawable.setColor(textColor)
            binding?.customButton12?.background = currentgradientDrawable
            binding?.customText12?.setTextColor(whiteColor)
            lastClickedTextView?.setTextColor(lastTextColor)
            lastgradientDrawable.setColor(lastBackColor)
            lastClickedView?.background = lastgradientDrawable
            lastClickedTextView = binding?.customText12
            lastClickedView = binding?.customButton12
            lastTextColor = textColor
            lastBackColor = grayColor
            binding?.mbtiimg?.setImageResource(R.drawable.esfj)
            binding?.mbtitext?.setText("派对灵魂欢乐颂")
        }
        binding?.customButton13?.setOnClickListener{
            val textColor : Int = ContextCompat.getColor(this, R.color.green_200)
            currentgradientDrawable.setColor(textColor)
            binding?.customButton13?.background = currentgradientDrawable
            binding?.customText13?.setTextColor(whiteColor)
            lastClickedTextView?.setTextColor(lastTextColor)
            lastgradientDrawable.setColor(lastBackColor)
            lastClickedView?.background = lastgradientDrawable
            lastClickedTextView = binding?.customText13
            lastClickedView = binding?.customButton13
            lastTextColor = textColor
            lastBackColor = grayColor
            binding?.mbtiimg?.setImageResource(R.drawable.infj)
            binding?.mbtitext?.setText("星际哲学家国王")
        }
        binding?.customButton14?.setOnClickListener{
            val textColor : Int = ContextCompat.getColor(this, R.color.green_200)
            currentgradientDrawable.setColor(textColor)
            binding?.customButton14?.background = currentgradientDrawable
            binding?.customText14?.setTextColor(whiteColor)
            lastClickedTextView?.setTextColor(lastTextColor)
            lastgradientDrawable.setColor(lastBackColor)
            lastClickedView?.background = lastgradientDrawable
            lastClickedTextView = binding?.customText14
            lastClickedView = binding?.customButton14
            lastTextColor = textColor
            lastBackColor = grayColor
            binding?.mbtiimg?.setImageResource(R.drawable.infp)
            binding?.mbtitext?.setText("梦幻星球追梦侠")
        }
        binding?.customButton15?.setOnClickListener{
            val textColor : Int = ContextCompat.getColor(this, R.color.green_200)
            currentgradientDrawable.setColor(textColor)
            binding?.customButton15?.background = currentgradientDrawable
            binding?.customText15?.setTextColor(whiteColor)
            lastClickedTextView?.setTextColor(lastTextColor)
            lastgradientDrawable.setColor(lastBackColor)
            lastClickedView?.background = lastgradientDrawable
            lastClickedTextView = binding?.customText15
            lastClickedView = binding?.customButton15
            lastTextColor = textColor
            lastBackColor = grayColor
            binding?.mbtiimg?.setImageResource(R.drawable.enfj)
            binding?.mbtitext?.setText("人生导航心灵师")
        }
        binding?.customButton16?.setOnClickListener{
            val textColor : Int = ContextCompat.getColor(this, R.color.green_200)
            currentgradientDrawable.setColor(textColor)
            binding?.customButton16?.background = currentgradientDrawable
            binding?.customText16?.setTextColor(whiteColor)
            lastClickedTextView?.setTextColor(lastTextColor)
            lastgradientDrawable.setColor(lastBackColor)
            lastClickedView?.background = lastgradientDrawable
            lastClickedTextView = binding?.customText16
            lastClickedView = binding?.customButton16
            lastTextColor = textColor
            lastBackColor = grayColor
            binding?.mbtiimg?.setImageResource(R.drawable.enfp)
            binding?.mbtitext?.setText("能量爆棚小火箭")
        }
        binding?.loadMoreButton?.setOnClickListener {
        }
    }

}