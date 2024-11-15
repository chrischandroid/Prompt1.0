package com.taichi.prompts.android.fragment.home

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.taichi.prompts.android.R
import com.taichi.prompts.android.BR
import com.taichi.prompts.android.adapter.HomeListAdapter
import com.taichi.prompts.android.databinding.FragmentHomeBinding
import com.taichi.prompts.android.repository.data.UserProfileMatchVOList
import com.taichi.prompts.base.BaseFragment
import com.taichi.prompts.base.adapter.AdapterCollectListener

/**
 * 首页
 */
class FragHome : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    private val adapter: HomeListAdapter = HomeListAdapter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModelId(): Int {
        return BR.fragHomeVm
    }

    override fun initViewData() {
        Log.i("HB","------------------------init")
        initListView()
        viewModel?.homeListData?.observe(viewLifecycleOwner) { list ->
            if (list != null && list?.isNotEmpty() == true) {
                binding?.homeTabListView?.post {
                    adapter.setDataList(list)
                }

            }

        }
    }

    override fun onResume() {
        super.onResume()
        viewModel?.getHomeList()
    }


    private fun initListView() {
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        binding?.homeTabListView?.layoutManager = manager
        binding?.homeTabListView?.adapter = adapter
    }

}
