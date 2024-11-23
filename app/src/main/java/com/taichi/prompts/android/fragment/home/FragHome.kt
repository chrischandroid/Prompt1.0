package com.taichi.prompts.android.fragment.home

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.taichi.prompts.android.R
import com.taichi.prompts.android.BR
import com.taichi.prompts.android.adapter.HomeListAdapter
import com.taichi.prompts.android.databinding.FragmentHomeBinding
import com.taichi.prompts.android.repository.data.UserProfileMatchVOList
import com.taichi.prompts.base.BaseFragment
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
        val itemDecoration = CardViewItemDecoration(resources.getDimensionPixelSize(R.dimen.dp_10))
        binding?.homeTabListView?.addItemDecoration(itemDecoration)
    }

}
class CardViewItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = spacing
        outRect.right = spacing

    }
}