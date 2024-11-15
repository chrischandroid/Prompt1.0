package com.taichi.prompts.android.fragment.common

import android.content.Intent
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


    override fun initViewData() {

    }


    private fun initListView() {
    }
}
