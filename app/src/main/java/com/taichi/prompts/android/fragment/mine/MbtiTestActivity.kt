package com.taichi.prompts.android.fragment.mine

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SPUtils
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.android.R
import com.taichi.prompts.android.BR
import com.taichi.prompts.android.databinding.ActivityMbtitestBinding
import com.taichi.prompts.android.databinding.ActivityMbtitestBindingImpl
import com.taichi.prompts.android.adapter.Question
import com.taichi.prompts.android.adapter.MbtiQuestionAdapter
import com.taichi.prompts.android.adapter.QuestionItem
import com.taichi.prompts.android.fragment.mine.MbtiTestViewModel
import com.taichi.prompts.android.repository.data.QuestionInfoVO
import com.taichi.prompts.base.BaseActivity

class MbtiTestActivity : BaseActivity<ActivityMbtitestBinding, MbtiTestViewModel>(){

    private lateinit var questionAdapter: MbtiQuestionAdapter
    private var questionList: MutableList<QuestionItem> = mutableListOf()
    val questionDetails : MutableMap<String, QuestionInfoVO> = mutableMapOf()

    val id = SPUtils.getInstance().getString(Constants.SP_USER_ID)

    override fun getLayoutId(): Int {
        return R.layout.activity_mbtitest
    }

    override fun getViewModelId(): Int {
        return BR.mbtiTestVm
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun initViewData() {
        questionList.add(QuestionItem("你更习惯于，依靠现有的经验和实际情况来做决定，还是依靠直觉和对未来的思考想象来做决定。", "经验", "直觉"))

        questionAdapter = MbtiQuestionAdapter(questionList)
        binding?.mbtiRecyclerView?.layoutManager = LinearLayoutManager(this)
        binding?.mbtiRecyclerView?.adapter = questionAdapter
        initClick()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initClick() {
        binding?.submitButton?.setOnClickListener {
            var map : MutableMap<String, Pair<String, QuestionInfoVO>> = mutableMapOf()
            questionAdapter.getQuestionList().forEachIndexed { index, questionItem ->
                val selected = when (questionItem.selectedOption) {
                    0 -> questionItem.option1
                    1 -> questionItem.option2
                    else -> "No Selection"
                }
                Log.d("MainActivity", "Question1 ${index + 1}: $selected")
                questionDetails[questionItem.question]?.let { info ->
                    map.put(questionItem.question, Pair(selected, info))
                }
            }
            if (!map.isEmpty()) {
                viewModel?.saveMbtiQuestion(id, map)
            }
        }

        viewModel?.qustionData?.observe(this) { list ->
            if (list != null && list?.isNotEmpty() == true) {
                val questionContents = mutableListOf<QuestionItem>()
                for (questionInfo in list) {
                    val questionContent = questionInfo.questionContent
                    val questionAvailableResults = questionInfo.availableResultVOList
                    questionDetails[questionContent] = questionInfo
                    if (questionAvailableResults.size >= 2) {
                        val firstKey = questionAvailableResults[0].label
                        val secondKey = questionAvailableResults[1].label
                        questionContents.add(QuestionItem(questionContent, firstKey, secondKey))
                    }
                }
                binding?.mbtiRecyclerView?.post {
                    questionAdapter.updateQuestions(questionContents)
                }
            }
        }

    }

}

