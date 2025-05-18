package com.taichi.prompts.android.fragment.common

import android.annotation.SuppressLint
import android.content.Intent
import androidx.core.content.ContextCompat
import com.taichi.prompts.android.BR
import com.taichi.prompts.android.R
import com.taichi.prompts.android.databinding.FragmentCommonBinding
import com.taichi.prompts.base.BaseFragment
import com.taichi.prompts.base.adapter.AdapterItemListener
import com.taichi.prompts.base.view.NoScrollLayoutManager

/**
 * 喜欢
 */
class FragCommon : BaseFragment<FragmentCommonBinding, CommonListViewModel>() {


    override fun getLayoutId(): Int {
        return R.layout.fragment_common
    }

    override fun getViewModelId(): Int {
        return BR.itemListVm
    }


    @SuppressLint("ResourceAsColor")
    override fun initViewData() {
        binding?.receive?.setOnClickListener {
            setTabSelection(true)
        }

        binding?.send?.setOnClickListener {
            setTabSelection(false)
        }

        // 默认选择"我收到的"
        setTabSelection(true)
    }

    private fun setTabSelection(isReceiveTab: Boolean) {
        // 获取颜色资源
        val blackColor = ContextCompat.getColor(requireContext(), R.color.black)
        val grayColor = ContextCompat.getColor(requireContext(), R.color.darkgray)

        binding?.receive?.setTextColor(if (isReceiveTab) blackColor else grayColor)
        binding?.send?.setTextColor(if (isReceiveTab) grayColor else blackColor)

        // 更新提示文本
        binding?.noseed?.text = if (isReceiveTab) {
            "暂无种子\n先去别处瞅瞅吧"
        } else {
            "暂无已经发出的种子\n先看看推荐吧"
        }
    }

}
