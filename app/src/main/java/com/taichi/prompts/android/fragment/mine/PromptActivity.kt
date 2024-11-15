package com.taichi.prompts.android.fragment.mine

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.SPUtils
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.android.R
import com.taichi.prompts.android.BR
import com.taichi.prompts.android.databinding.ActivityPromptBinding
import com.taichi.prompts.android.databinding.ActivityPromptBindingImpl
import com.taichi.prompts.android.adapter.Question
import com.taichi.prompts.android.adapter.QuestionAdapter
import com.taichi.prompts.android.fragment.mine.PromptViewModel
import com.taichi.prompts.base.BaseActivity

class PromptActivity : BaseActivity<ActivityPromptBinding, PromptViewModel>(){

    val questionList = listOf(
        Question("我有一个特殊的技能是:"),
        Question("在周末或者不忙的时候我会:"),
        Question("我会告诉年轻时候的自己:"),
        Question("我对于这件事情有很大的热忱"),
        Question("我的上一段关系教给了我"),
        Question("在别人如此做的时候我获得了极大的满足"),
        Question("我做过最自豪的事情是"),
        Question("我生命中最糟糕的事件"),
        Question("你认为现代爱情应该是怎样的？"),
        Question("最不能忍受的饮食习惯是什么？"),
        Question("描述一次你印象深刻的国内旅行经历。"),
        Question("你最喜欢的中国传统节日是哪一个？为什么？"),
        Question("你最擅长的厨艺是什么？"),
        Question("一部改变了你看法的中国电影是？"),
        Question("你周末喜欢如何度过？"),
        Question("如果能与一位历史人物共进晚餐，你会选择谁？"),
        Question("你的梦想职业是什么？"),
        Question("最想探访的中国城市或景点？"),
        Question("描述一个完美的休息日该有的样子。"),
        Question("你最喜欢的中国街头小吃是什么？"),
        Question("一首形容你生活态度的歌曲是？"),
        Question("你在家里最喜欢的角落是哪里？"),
        Question("你对怎样的恋爱速度感到舒适？"),
        Question("描述一个你的创造性才能。"),
        Question("你的座右铭是什么？"),
        Question("你最重视哪种家庭传统？"),
        Question("有没有什么习惯是你特别想改掉的？"),
        Question("在朋友圈中，你通常是什么角色？"),
        Question("你最喜欢的中国古代故事或神话是什么？"),
        Question("有哪些书籍对你影响很大？"),
        Question("你如何看待数字时代的人际关系？"),
        Question("你是早起的鸟儿还是夜猫子？"),
        Question("你在哪些事情上是个完美主义者？"),
        Question("介绍一项你认为别人不会的技能。"),
        Question("你对宠物有什么特别的感情吗？"),
        Question("你如何平衡工作和生活？"),
        Question("你在社交媒体上最活跃还是现实中更活跃？"),
        Question("如果有机会，你想改变世界上的什么？"),
        Question("你的理想假日活动是什么？"),
        Question("你最看重恋人的哪种品质？"),
        Question("你认为最重要的家庭价值观是什么？"),
        Question("在休息日最喜欢做的一件事？"),
        Question("你未来想在哪座城市定居？")
    )
    private val adapter = QuestionAdapter(this, questionList)
    val id = SPUtils.getInstance().getString(Constants.SP_USER_ID)

    override fun getLayoutId(): Int {
        return R.layout.activity_prompt
    }

    override fun getViewModelId(): Int {
        return BR.promptVm
    }

    override fun initViewData() {
        binding?.recyclerView?.layoutManager = LinearLayoutManager(this)
        binding?.recyclerView?.adapter = adapter
        initClick()
    }

    private fun initClick() {
        binding?.loadMoreButton?.setOnClickListener {
            val map : MutableMap<String, String> = adapter.getPromtMap()
            if (!map.isEmpty()) {
                viewModel?.saveQuestion(id, map)
            }
        }
    }

}