package com.fera.kuiz.feat_CategoryQuestions.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fera.kuiz.BuildConfig
import com.fera.kuiz.R
import com.fera.kuiz.common.util.Const
import com.fera.kuiz.common.util.EnumQuestionType
import com.fera.kuiz.databinding.ListItemQuestionBinding
import com.fera.kuiz.feat_CategoryQuestions.model.question.TblQuestion
import com.fera.kuiz.feat_takeQuiz.view.TakeQuizActivity

class AdapterQuestion(private var listQuestion: List<TblQuestion>, private val context: Context, private val actions: InterfaceAdapterQuestion) : RecyclerView.Adapter<AdapterQuestion.MyViewHolder>() {

    interface InterfaceAdapterQuestion {
        fun gotoTakeQuizActivity(pkCategoryId: Long, continueQuestion: Boolean, questionNo: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = ListItemQuestionBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.b.apply {
            val tblQuestion = listQuestion[position]

            tvQuestionNoLiCat.text = tblQuestion.questionNo.toString()
            tvQuestionLiCat.text = tblQuestion.question

            when(tblQuestion.questionType){
                EnumQuestionType.MULTIPLE_CHOICE.type -> {
                    Glide.with(context)
                        .load(R.drawable.ic_multi_choice)
                        .into(ivQuestionTypeLiCat)
                }
                EnumQuestionType.TRUE_OR_FALSE.type -> {
                    Glide.with(context)
                        .load(R.drawable.ic_multi_choice)
                        .into(ivQuestionTypeLiCat)
                }
            }

            root.setOnClickListener {
                actions.gotoTakeQuizActivity(tblQuestion.fkQuestion_categoryId, true, tblQuestion.questionNo-1)
            }
        }
    }

    override fun getItemCount(): Int {
        return listQuestion.size
    }

    fun updateList(list: List<TblQuestion>){
        listQuestion = list
        notifyDataSetChanged()
    }

    class MyViewHolder(val b: ListItemQuestionBinding): RecyclerView.ViewHolder(b.root)
}