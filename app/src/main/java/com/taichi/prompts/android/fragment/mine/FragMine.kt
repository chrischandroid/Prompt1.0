package com.taichi.prompts.android.fragment.mine

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.taichi.prompts.android.databinding.FragmentMineBinding
import com.taichi.prompts.android.BR
import com.taichi.prompts.android.R
import com.taichi.prompts.android.activity.login.LoginActivity
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.base.BaseFragment
import com.tencent.imsdk.v2.V2TIMConversation
import com.tencent.qcloud.tuicore.TUIConstants
import com.tencent.qcloud.tuicore.TUICore

/**
 * 我的页面
 */
class FragMine : BaseFragment<FragmentMineBinding, MineViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun getViewModelId(): Int {
        return BR.mineVm
    }

    override fun initViewData() {
        initClick()
    }

    private fun initClick() {
        if (binding?.headImg!= null) {
            Glide.with(this)
                .load(R.drawable.default_img)
                .placeholder(R.drawable.default_img)
                .error(R.drawable.default_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions().fitCenter())
                .into(binding!!.headImg)
        }
    }
}
