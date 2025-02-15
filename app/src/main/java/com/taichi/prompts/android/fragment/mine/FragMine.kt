package com.taichi.prompts.android.fragment.mine

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.room.Room
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
import com.taichi.prompts.base.AppDatabase
import com.taichi.prompts.base.BaseFragment
import com.tencent.imsdk.v2.V2TIMConversation
import com.tencent.qcloud.tuicore.TUIConstants
import com.tencent.qcloud.tuicore.TUICore
import java.io.File

/**
 * 我的页面
 */
class FragMine : BaseFragment<FragmentMineBinding, MineViewModel>() {
    private lateinit var database: AppDatabase

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
        database = Room.databaseBuilder(
            requireContext().applicationContext,
            AppDatabase::class.java,
            "image-database"
        ).build()

        loadAvatar()

        binding?.modify?.setOnClickListener{
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        loadAvatar()
    }

    private fun loadAvatar() {
        Thread {
            val avatar = database.avatarDao().getAvatar()
            avatar?.let {
                val privateImagePath = it.imagePath
                requireActivity().runOnUiThread {
                    loadImageWithGlide(privateImagePath)
                }
            }
            val name = SPUtils.getInstance().getString("userNickName")
            name?.let {
                requireActivity().runOnUiThread {
                    val text1 = view?.findViewById<TextView>(R.id.username)
                    text1?.text = name
                }
            }
        }.start()
    }

    private fun loadImageWithGlide(imagePath: String) {
        if (binding?.headImg != null) {
            val file = File(imagePath)
            if (file.exists()) {
                Glide.with(this)
                    .load(file)
                    .placeholder(R.drawable.default_img)
                    .error(R.drawable.default_img)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(RequestOptions().fitCenter())
                    .into(binding!!.headImg)
            } else {
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
}
