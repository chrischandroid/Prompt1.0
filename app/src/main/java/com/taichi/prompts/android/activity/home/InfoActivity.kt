package com.taichi.prompts.android.activity.home

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.taichi.prompts.android.R
import com.taichi.prompts.android.BR
import com.taichi.prompts.base.BaseActivity
import com.taichi.prompts.android.databinding.ActivityInfoBinding


class InfoActivity : BaseActivity<ActivityInfoBinding, InfoViewModel>()  {

    override fun getLayoutId(): Int {
        return R.layout.activity_info
    }

    override fun getViewModelId(): Int {
        return BR.infoVm
    }

    override fun initViewData() {
        binding?.name?.setText(intent.getStringExtra("name"))
        binding?.careerTag?.setText(intent.getStringExtra("career"))
        binding?.schoolId?.setText(intent.getStringExtra("school"))
        binding?.hometag?.setText(intent.getStringExtra("hometown"))
        binding?.holdtag?.setText(intent.getStringExtra("asset"))
        binding?.mbti?.setText(intent.getStringExtra("mbti"))
        binding?.slender?.setText(intent.getStringExtra("weight")+"kg/"+ intent.getStringExtra("height") +"cm")
        binding?.age?.setText(intent.getStringExtra("age"))
        binding?.introduction?.setText(intent.getStringExtra("about"))
        Glide.with(this)
            .load(intent.getStringExtra("headImg"))
            .placeholder(R.drawable.default_img)
            .error(R.drawable.default_img)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .apply(RequestOptions().fitCenter())
            .into(binding!!.imageView)
        binding?.back?.setOnClickListener{
            finish()
        }
    }
}