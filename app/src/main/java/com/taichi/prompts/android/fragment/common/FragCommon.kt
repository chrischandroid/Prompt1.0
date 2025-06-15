package com.taichi.prompts.android.fragment.common

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.taichi.prompts.android.BR
import com.taichi.prompts.android.R
import com.taichi.prompts.android.adapter.PhotoAdapter
import com.taichi.prompts.android.databinding.FragmentCommonBinding
import com.taichi.prompts.android.fragment.mine.SeedActivity
import com.taichi.prompts.base.BaseFragment


/**
 * 喜欢
 */
class FragCommon : BaseFragment<FragmentCommonBinding, CommonListViewModel>() {

    private var isPageOne: Boolean = true
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PhotoAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_common
    }

    override fun getViewModelId(): Int {
        return BR.itemListVm
    }


    @SuppressLint("ResourceAsColor")
    override fun initViewData() {
        setupRecyclerView()
        binding?.receive?.setOnClickListener {
            viewModel?.getReceiveList()
            setTabSelection(true)
        }

        binding?.send?.setOnClickListener {
            viewModel?.getSendList()
            setTabSelection(false)
        }
        viewModel?.getReceiveList()
        setTabSelection(true)
        viewModel?.receiveListData?.observe(viewLifecycleOwner) { list ->
            if (list != null && list.isNotEmpty() && isPageOne) {
                hideLoading()
                adapter.updateData(list)
            }
        }
        viewModel?.sendListData?.observe(viewLifecycleOwner) { list ->
            if (list != null && list.isNotEmpty() && !isPageOne) {
                hideLoading()
                adapter.updateData(list)
            }
        }
    }

    private fun setupRecyclerView() {
        recyclerView = binding?.recyclerView!!
        adapter = PhotoAdapter()
        adapter.setOnItemClickListener { item ->
            val intent = Intent(requireContext(), SeedActivity::class.java)
            // 传递数据到新Activity
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        val layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = layoutManager

        recyclerView.addItemDecoration(
            GridSpacingItemDecoration(2, dpToPx(8), true)
        )
    }

    private fun setTabSelection(isReceiveTab: Boolean) {
        isPageOne = isReceiveTab
        val blackColor = ContextCompat.getColor(requireContext(), R.color.black)
        val grayColor = ContextCompat.getColor(requireContext(), R.color.darkgray)

        binding?.receive?.setTextColor(if (isReceiveTab) blackColor else grayColor)
        binding?.send?.setTextColor(if (isReceiveTab) grayColor else blackColor)

        binding?.noseed?.text = if (isReceiveTab) {
            "暂无种子\n先去别处瞅瞅吧"
        } else {
            "暂无已经发出的种子\n先看看推荐吧"
        }
    }

    private fun hideLoading() {
        binding?.recyclerView?.visibility = View.VISIBLE
        binding?.noseed?.visibility = View.GONE
        binding?.noseedview?.visibility = View.GONE
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

}

class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: Int,
    private val includeEdge: Boolean
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // item position
        if (position == RecyclerView.NO_POSITION) return

        val column = position % spanCount // item column

        if (includeEdge) {
            // spacing - column * ((1f / spanCount) * spacing)
            outRect.left = spacing - column * spacing / spanCount
            // (column + 1) * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount

            if (position < spanCount) { // top edge
                outRect.top = spacing
            }
            outRect.bottom = spacing // item bottom
        } else {
            // column * ((1f / spanCount) * spacing)
            outRect.left = column * spacing / spanCount
            // spacing - (column + 1) * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount) {
                outRect.top = spacing // item top
            }
        }
    }
}