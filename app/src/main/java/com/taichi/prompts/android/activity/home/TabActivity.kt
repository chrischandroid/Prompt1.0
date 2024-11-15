package com.taichi.prompts.android.activity.home

import android.graphics.BitmapFactory
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
import com.tencent.qcloud.tuikit.tuicontact.classicui.pages.TUIContactFragment
import com.tencent.qcloud.tuikit.tuiconversation.classicui.page.TUIConversationFragment
import com.tencent.qcloud.tuikit.tuiconversation.classicui.page.TUIConversationFragmentContainer

class TabActivity : BaseActivity<ActivityTabBinding, TabViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_tab
    }

    override fun getViewModelId(): Int {
        return BR.homeVm
    }

    override fun initViewData() {
        initPageModule()
        val icons = arrayOf(
            BitmapFactory.decodeResource(resources, R.drawable.icon_home_selected),
            BitmapFactory.decodeResource(resources, R.drawable.icon_love_red),
            BitmapFactory.decodeResource(resources, R.drawable.icon_community_selected),
            BitmapFactory.decodeResource(resources, R.drawable.icon_personal_selected)
        )
        val icons2 = arrayOf(
            BitmapFactory.decodeResource(resources, R.drawable.icon_home_grey),
            BitmapFactory.decodeResource(resources, R.drawable.icon_love_grey),
            BitmapFactory.decodeResource(resources, R.drawable.icon_community_grey),
            BitmapFactory.decodeResource(resources, R.drawable.icon_personal_grey)
        )
        val tabTexts = arrayOf("首页", "喜欢", "聊天", "个人")
        binding?.tabBottomBar?.let {
            it.setSelectedIcons(icons.toList())
                .setUnselectIcons(icons2.toList())
                .setTabText(tabTexts.toList())
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
