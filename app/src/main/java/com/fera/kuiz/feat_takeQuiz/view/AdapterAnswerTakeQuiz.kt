package com.fera.kuiz.feat_takeQuiz.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fera.kuiz.R
import com.fera.kuiz.common.util.GlobalProperties
import com.fera.kuiz.databinding.ListItemChoiceTakeQBinding
import com.fera.kuiz.feat_CategoryQuestions.model.answer.TblAnswer
import com.fera.kuiz.feat_CategoryQuestions.model.userAnswer.TblUserAnswer
import java.util.Date

class AdapterAnswerTakeQuiz(private var listAnswer: List<TblAnswer>, private val context: Context, private val actions: InterfaceAdapterAnswer) : RecyclerView.Adapter<AdapterAnswerTakeQuiz.MyViewHolder>() {

    interface InterfaceAdapterAnswer {
        fun selectUserAnswer(tblUserAnswer: TblUserAnswer)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val b = ListItemChoiceTakeQBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(b)
    }

    private var previousPosition = RecyclerView.NO_POSITION
    private var selectedPosition = RecyclerView.NO_POSITION
    private var showAnswer = false

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tblAnswer = listAnswer[position]

        holder.b.apply {
            tvChoiceLiTakeQuiz.text = tblAnswer.answer
            tvChoiceDescriptionLiTakeQuiz.text = tblAnswer.description

            if (tblAnswer.isCorrect) {
                ivChoiceIndicatorLiTakeQuiz.setImageResource(R.drawable.ic_check)
                ivChoiceIndicatorLiTakeQuiz.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green_dark))
            } else {
                ivChoiceIndicatorLiTakeQuiz.setImageResource(R.drawable.ic_close)
                ivChoiceIndicatorLiTakeQuiz.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))
            }

            if (selectedPosition == position){
                holder.toggleVisibility(true)
                selectedPosition = RecyclerView.NO_POSITION
            } else {
                holder.toggleVisibility(false)
            }

            root.setOnClickListener {
                selectedPosition = position

                val tblUserAnswer = TblUserAnswer(tblAnswer.pkAnswerId, tblAnswer.fkAnswer_questionId, GlobalProperties.pkUserRegistrationId, tblAnswer.answer, tblAnswer.isCorrect, Date().time)
                actions.selectUserAnswer(tblUserAnswer)
                notifyDataSetChanged()
            }

            if (showAnswer){
                if (tblAnswer.isCorrect){
                    holder.toggleVisibility(showAnswer)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listAnswer.size
    }

    fun toggleAnswer(boolean: Boolean){
        showAnswer = boolean
        notifyDataSetChanged()
    }

    fun updateList(list: List<TblAnswer>) {
        listAnswer = list
        notifyDataSetChanged()
    }

    class MyViewHolder(val b: ListItemChoiceTakeQBinding) : RecyclerView.ViewHolder(b.root) {
        fun toggleVisibility(show: Boolean) {
            if (show) {
                b.apply {
                    ivChoiceIndicatorLiTakeQuiz.visibility = View.VISIBLE
                    tvChoiceDescriptionLiTakeQuiz.visibility = View.VISIBLE
                }
            } else {
                b.apply {
                    ivChoiceIndicatorLiTakeQuiz.visibility = View.GONE
                    tvChoiceDescriptionLiTakeQuiz.visibility = View.GONE
                }
            }
        }


    }

}
