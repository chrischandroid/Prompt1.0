package com.taichi.prompts.android.fragment.mine

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
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
        initObserverData()
    }

    private fun initClick() {
        //登录
        binding?.mineUserName?.setOnClickListener {
            if(viewModel?.loginState?.get() == true){
                return@setOnClickListener
            }
            login()
        }

        binding?.mineProfileEdit?.setOnClickListener{
            if(viewModel?.loginState?.get() != true){
                return@setOnClickListener
            }
            editProfile()
        }
        binding?.minePreferEdit?.setOnClickListener{
            startConversation()
        }
        binding?.minePromptEdit?.setOnClickListener{
            if(viewModel?.loginState?.get() != true){
                return@setOnClickListener
            }
            startPrompt()
        }

        binding?.mineMBTIEdit?.setOnClickListener{
            if(viewModel?.loginState?.get() != true){
                return@setOnClickListener
            }
            startMBTITest()
        }

        //登录
        binding?.mineUserHead?.setOnClickListener {
            if(viewModel?.loginState?.get() == true){
                return@setOnClickListener
            }
            login()
        }

        //登出
        binding?.mineLoginOut?.setOnClickListener {
            viewModel?.logOut()
        }

        //进入我的收藏
    }

    private fun initObserverData() {
        viewModel?.logoutState?.observe(viewLifecycleOwner) {
            if (it == true) {
                ToastUtils.showShort("账号已退出登录")
            }
        }
    }

    private fun login() {
        activity?.finish()
        context?.startActivity(Intent(context, LoginActivity::class.java))
    }

    private fun editProfile() {
        context?.startActivity(Intent(context, ProfileActivity::class.java))
    }

    private fun startConversation() {
        val id = SPUtils.getInstance().getString(Constants.SP_USER_ID)
        Log.e("TUI----------", id)

        val param = Bundle()
        param.putInt(TUIConstants.TUIChat.CHAT_TYPE, V2TIMConversation.V2TIM_C2C)
        param.putString(TUIConstants.TUIChat.CHAT_ID, "@RBT#001")
        TUICore.startActivity("TUIC2CChatActivity", param)

    }

    private fun startPrompt() {
        context?.startActivity(Intent(context, PromptActivity::class.java))
    }

    private fun startMBTITest() {
        context?.startActivity(Intent(context, TestActivity::class.java))
    }
}
