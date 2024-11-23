package com.taichi.prompts.android.adapter

import android.view.LayoutInflater
import com.taichi.prompts.android.R
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class QuestionItem(
    val question: String,
    val option1: String,
    val option2: String,
    var selectedOption: Int = -1  // -1 表示未选择，0 表示选项1，1 表示选项2
)

class MbtiQuestionAdapter(private var questionList: MutableList<QuestionItem>) :
    RecyclerView.Adapter<MbtiQuestionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.test_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val questionItem = questionList[position]

        // 设置问题文本
        holder.questionTextView.text = questionItem.question

        // 设置选项文本
        holder.option1RadioButton.text = questionItem.option1
        holder.option2RadioButton.text = questionItem.option2

        // 根据当前选择设置单选按钮的选中状态
        holder.option1RadioButton.isChecked = (questionItem.selectedOption == 0)
        holder.option2RadioButton.isChecked = (questionItem.selectedOption == 1)

        // 设置单选按钮的点击监听器
        holder.option1RadioButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                questionItem.selectedOption = 0
                holder.option2RadioButton.isChecked = false // 确保只有一个选项被选中
            }
        }
        holder.option2RadioButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                questionItem.selectedOption = 1
                holder.option1RadioButton.isChecked = false // 确保只有一个选项被选中
            }
        }
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    fun getQuestionList(): MutableList<QuestionItem> {
        return questionList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionTextView: TextView = itemView.findViewById(R.id.questionTextView)
        val option1RadioButton: RadioButton = itemView.findViewById(R.id.option1RadioButton)
        val option2RadioButton: RadioButton = itemView.findViewById(R.id.option2RadioButton)
    }

    fun updateQuestions(questions: MutableList<QuestionItem>) {
        this.questionList = questions
        notifyDataSetChanged()
    }

}