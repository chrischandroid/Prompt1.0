package com.taichi.prompts.android.fragment.home

import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
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
    private var startX = 0f
    private var startY = 0f

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModelId(): Int {
        return BR.fragHomeVm
    }

    override fun initViewData() {
        initListView()
        viewModel?.homeListData?.observe(viewLifecycleOwner) { list ->
            if (list != null && list.isNotEmpty()) {
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

        binding?.homeTabListView?.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.x
                    startY = event.y
                    // 按下时请求父布局不拦截事件
                    v.parent.requestDisallowInterceptTouchEvent(true)
                }
                MotionEvent.ACTION_MOVE -> {
                    val dx = event.x - startX
                    val dy = event.y - startY
                    // 判断是否为横向滑动
                    if (Math.abs(dx) > Math.abs(dy)) {
                        // 横向滑动时请求父布局不拦截事件
                        v.parent.requestDisallowInterceptTouchEvent(true)
                    } else {
                        // 纵向滑动时允许父布局拦截事件
                        v.parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // 抬起或取消时允许父布局拦截事件
                    v.parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            // 将事件传递给 RecyclerView 处理
            v.onTouchEvent(event)
            return@setOnTouchListener true
        }
    }
}

class CardViewItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = spacing
        outRect.right = spacing
    }
}