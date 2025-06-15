package com.taichi.prompts.android.fragment.mine

import com.taichi.prompts.android.R
import com.taichi.prompts.android.BR
import com.taichi.prompts.android.databinding.ActivitySeedBinding
import com.taichi.prompts.base.BaseActivity

class SeedActivity : BaseActivity<ActivitySeedBinding, SeedViewModel>()  {

    override fun getLayoutId(): Int {
        return R.layout.activity_seed
    }

    override fun getViewModelId(): Int {
        return BR.seedVm
    }

    override fun initViewData() {

    }
}