package com.taichi.prompts.android.activity.home

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.VectorDrawable
import android.graphics.Canvas
import androidx.activity.OnBackPressedDispatcher
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.LogUtils
import com.taichi.prompts.android.BR
import com.taichi.prompts.android.R
import com.taichi.prompts.android.databinding.ActivityTabBinding
import com.taichi.prompts.android.fragment.home.FragHome
import com.taichi.prompts.android.fragment.common.FragCommon
import com.taichi.prompts.android.fragment.mine.FragMine
import com.taichi.prompts.base.BaseActivity
import com.taichi.prompts.base.adapter.Pager2Adapter
import com.taichi.prompts.base.tab.NavigationBottomBar
import com.tencent.qcloud.tuikit.tuiconversation.classicui.page.TUIConversationFragmentContainer

class TabActivity : BaseActivity<ActivityTabBinding, TabViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_tab
    }

    override fun getViewModelId(): Int {
        return BR.homeVm
    }


    fun vectorDrawableToBitmap(context: Context, drawableResId: Int, width: Int, height: Int): Bitmap? {
        val drawable = ContextCompat.getDrawable(context, drawableResId) as? VectorDrawable ?: return null
        drawable.setBounds(0, 0, width, height)

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.draw(canvas)

        return bitmap
    }

    override fun initViewData() {
        initPageModule()
        val icons = arrayOf(
            vectorDrawableToBitmap(this, R.drawable.fill1, 200, 200),
            vectorDrawableToBitmap(this, R.drawable.love1, 200, 200),
            vectorDrawableToBitmap(this, R.drawable.msg1, 200, 200),
            vectorDrawableToBitmap(this, R.drawable.mine1, 200, 200)
        )

        val icons2 = arrayOf(
            vectorDrawableToBitmap(this, R.drawable.fill2, 100, 100),
            vectorDrawableToBitmap(this, R.drawable.love2, 100, 100),
            vectorDrawableToBitmap(this, R.drawable.msg2, 100, 100),
            vectorDrawableToBitmap(this, R.drawable.mine2, 100, 100)
        )
        //val tabTexts = arrayOf("首页", "喜欢", "聊天", "个人")
        binding?.tabBottomBar?.let {
            it.setSelectedIcons(icons.toList() as List<Bitmap>?)
                .setUnselectIcons(icons2.toList() as List<Bitmap>?)
                .setupViewpager(binding?.tabViewPager2)
                .start()
        }
        binding?.tabBottomBar?.registerTabClickListener(object :
            NavigationBottomBar.OnBottomTabClickListener {
            override fun tabClick(position: Int) {
                LogUtils.d("registerTabClickListener position=$position")
            }
        })
    }

    private fun initPageModule() {
        val pageFragList = mutableListOf<Fragment>()
        pageFragList.add(FragHome())
        pageFragList.add(FragCommon())
        pageFragList.add(TUIConversationFragmentContainer())
        pageFragList.add(FragMine())

        val pageAdapter = Pager2Adapter(this)
        pageAdapter.setData(pageFragList)
        //默认不做预加载Fragment
        binding?.tabViewPager2?.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        binding?.tabViewPager2?.adapter = pageAdapter
    }

}
