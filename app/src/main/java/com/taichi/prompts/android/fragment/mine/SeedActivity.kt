package com.taichi.prompts.android.fragment.mine
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.taichi.prompts.android.R
import com.taichi.prompts.android.BR
import com.taichi.prompts.android.databinding.ActivitySeedBinding
import com.taichi.prompts.base.BaseActivity
import com.tencent.imsdk.v2.V2TIMConversation
import com.tencent.qcloud.tuicore.TUIConstants
import com.tencent.qcloud.tuicore.TUICore

class SeedActivity : BaseActivity<ActivitySeedBinding, SeedViewModel>()  {

    override fun getLayoutId(): Int {
        return R.layout.activity_seed
    }

    override fun getViewModelId(): Int {
        return BR.seedVm
    }

    override fun initViewData() {
        viewModel?.getSeedDetail(intent.getLongExtra("seedId", 0))
        viewModel?.seedData?.observe(this) { data ->
            if (data != null) {
                binding?.username?.text = data.nickName
                binding?.propmtQuestion?.text = data.promptQuestion
                binding?.promptAnswer?.text = data.propmtAnswer
                Glide.with(this)
                    .load(data.headImgUrl)
                    .placeholder(R.drawable.default_img)
                    .error(R.drawable.default_img)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(RequestOptions().fitCenter())
                    .into(binding!!.headImg)
            }
        }
        binding?.button2?.setOnClickListener {
            val param = Bundle()
            param.putInt(TUIConstants.TUIChat.CHAT_TYPE, V2TIMConversation.V2TIM_C2C)
            param.putString(TUIConstants.TUIChat.CHAT_ID, intent.getStringExtra("oppositeUserId"))
            TUICore.startActivity("TUIC2CChatActivity", param)
        }
        binding?.button1?.setOnClickListener {
            finish()
        }
        binding?.frameBack?.setOnClickListener {
            finish()
        }
    }
}