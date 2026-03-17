package com.fera.kuiz.feat_takeQuiz.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fera.kuiz.R
import com.fera.kuiz.common.util.GlobalProperties
import com.fera.kuiz.databinding.ListItemChoiceTakeQBinding
import com.fera.kuiz.feat_AddQuestion.model.answer.TblAnswer
import com.fera.kuiz.feat_takeQuiz.model.userAnswer.TblUserAnswer
import java.util.Date

class AdapterAnswerTakeQuizAct(private var listAnswer: List<TblAnswer>, private val context: Context, private val actions: InterfaceAdapterAnswer) : RecyclerView.Adapter<AdapterAnswerTakeQuizAct.MyViewHolder>() {

    interface InterfaceAdapterAnswer {
        fun selectUserAnswer(tblUserAnswer: TblUserAnswer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val b = ListItemChoiceTakeQBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(b, context)
    }

    private var selectedPosition = RecyclerView.NO_POSITION
    private var showAnswer = false

    override fun onBindViewHolder(holder: MyViewHolder, @SuppressLint("RecyclerView") position: Int) {
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
                holder.toggleVisibility(true, tblAnswer.isCorrect)
                selectedPosition = RecyclerView.NO_POSITION
            } else {
                holder.toggleVisibility(false, tblAnswer.isCorrect)
            }

            root.setOnClickListener {
                selectedPosition = position

                val tblUserAnswer = TblUserAnswer(tblAnswer.pkAnswerId, tblAnswer.fkAnswer_questionId, GlobalProperties.pkUserRegistrationId, tblAnswer.answer, tblAnswer.isCorrect, Date().time)
                actions.selectUserAnswer(tblUserAnswer)
                notifyDataSetChanged()
            }

            if (showAnswer){
                if (tblAnswer.isCorrect){
                    holder.toggleVisibility(showAnswer, tblAnswer.isCorrect)
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

    class MyViewHolder(val b: ListItemChoiceTakeQBinding, val context: Context) : RecyclerView.ViewHolder(b.root) {
        fun toggleVisibility(show: Boolean, isCorrect: Boolean) {
            if (show) {
                b.apply {
                    if (isCorrect){
                        clChoiceLi.background = ContextCompat.getDrawable(context, R.drawable.bg_rect_r5_choice_correct)
                    } else {
                        clChoiceLi.background = ContextCompat.getDrawable(context, R.drawable.bg_rect_r5_choice_wrong)
                    }
                    ivChoiceIndicatorLiTakeQuiz.visibility = View.VISIBLE
                    tvChoiceDescriptionLiTakeQuiz.visibility = View.VISIBLE
                }
            } else {
                b.apply {
                    clChoiceLi.background = ContextCompat.getDrawable(context, R.drawable.bg_rect_r5_choice)
                    ivChoiceIndicatorLiTakeQuiz.visibility = View.GONE
                    tvChoiceDescriptionLiTakeQuiz.visibility = View.GONE
                }
            }
        }


    }

}
