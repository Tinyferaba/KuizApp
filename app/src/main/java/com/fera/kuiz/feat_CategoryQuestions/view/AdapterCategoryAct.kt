package com.fera.kuiz.feat_CategoryQuestions.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fera.kuiz.R
import com.fera.kuiz.common.util.EnumQuestionType
import com.fera.kuiz.databinding.ListItemQuestionBinding
import com.fera.kuiz.feat_AddQuestion.model.question.TblQuestion

class AdapterCategoryAct(private var listQuestion: List<TblQuestion>, private val context: Context, private val actions: InterfaceAdapterQuestion) : RecyclerView.Adapter<AdapterCategoryAct.MyViewHolder>() {

    interface InterfaceAdapterQuestion {
        fun gotoTakeQuizActivity(pkCategoryId: Long, continueQuestion: Boolean, questionNo: Int)
        fun editQuestion(tblQuestion: TblQuestion)
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

            if (tblQuestion.isTaken)
                ivTakeStatusLiCat.visibility = View.VISIBLE
            else
                ivTakeStatusLiCat.visibility = View.GONE


            when(tblQuestion.questionType){
                EnumQuestionType.MULTIPLE_CHOICE.type -> {
                    Glide.with(context)
                        .load(R.drawable.ic_multi_choice)
                        .into(ivQuestionTypeLiCat)
                }
                EnumQuestionType.TRUE_OR_FALSE.type -> {
                    Glide.with(context)
                        .load(R.drawable.ic_true_or_false)
                        .into(ivQuestionTypeLiCat)
                }
            }

            ivEditLiCat.setOnClickListener {
                actions.editQuestion(tblQuestion)
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