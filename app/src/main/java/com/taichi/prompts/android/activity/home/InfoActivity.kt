package com.taichi.prompts.android.activity.home

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

    }
}