package com.fera.kuiz.feat_main.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fera.kuiz.BuildConfig
import com.fera.kuiz.common.util.Const
import com.fera.kuiz.databinding.ListItemRecentCatBinding
import com.fera.kuiz.feat_takeQuiz.model.TblCategory
import com.fera.kuiz.feat_takeQuiz.view.TakeQuizActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdapterRecentCat(private var listRecentCat: List<TblCategory>, private val context: Context, private val actions: InterfaceAdapterRecentCat): RecyclerView.Adapter<AdapterRecentCat.MyViewHolder>() {

    interface InterfaceAdapterRecentCat {
        fun gotoTakeQuizActivity(pkCategoryId: Long, continueQuestion: Boolean, questionNo: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val b = ListItemRecentCatBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(b)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.b.apply {
            val tblCategory = listRecentCat[position]
            tvCategoryLiRecent.text = tblCategory.title
            pbCorrectLiRecent.max = tblCategory.totalQAnswered
            pbCorrectLiRecent.progress = tblCategory.totalCorrectAnswers
            pbProgressLiRecent.max = tblCategory.totalQuestions
            pbProgressLiRecent.progress = tblCategory.totalQAnswered

            root.setOnClickListener {
                actions.gotoTakeQuizActivity(tblCategory.pkCategoryId, true, tblCategory.lastQuestionTakenNo)
            }
        }
    }

    override fun getItemCount(): Int {
       return listRecentCat.size
    }

    fun updateList(list: List<TblCategory>){
        listRecentCat = list
        notifyDataSetChanged()
    }


    class MyViewHolder(val b: ListItemRecentCatBinding): RecyclerView.ViewHolder(b.root){

    }
}