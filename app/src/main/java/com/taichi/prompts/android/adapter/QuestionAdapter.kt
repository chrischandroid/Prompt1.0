package com.taichi.prompts.android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.taichi.prompts.android.R

data class Question(val text: String)
class QuestionAdapter(private val context: Context, private var questionList: List<Question>) :
    RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {
    private val extraInfoMap: MutableMap<String, String> = mutableMapOf()
    private var isChange : Boolean = false

    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionTextView: TextView = itemView.findViewById(R.id.question_text_view)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                val question = questionList[position].text
                showAnswerDialog(question)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.question_item, parent, false)
        return QuestionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.questionTextView.text = questionList[position].text
    }

    override fun getItemCount(): Int = questionList.size

    private fun showAnswerDialog(question: String) {
        val dialog = BottomSheetDialog(context)
        val view = LayoutInflater.from(context).inflate(R.layout.answer_dialog, null)

        dialog.setContentView(view)
        dialog.show()
        val confirmButton = view.findViewById<Button>(R.id.confirm_button)
        val answerEditText = view.findViewById<EditText>(R.id.answer_edit_text)
        val cancelButton = view.findViewById<Button>(R.id.cancel_button)

        confirmButton.setOnClickListener {
            val answer = answerEditText.text.toString()
            if (!answer.isEmpty()) {
                extraInfoMap[question] = answer
                isChange = true
            }
            dialog.dismiss()
        }
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun updateQuestions(newList: List<Question>) {
        this.questionList = newList
        notifyDataSetChanged()
    }

    fun getPromtMap() : MutableMap<String, String> {
        if (!isChange || extraInfoMap.isEmpty()) {
            return mutableMapOf< String, String>()
        }
        return mutableMapOf<String, String>().apply { putAll(extraInfoMap) }
    }
}