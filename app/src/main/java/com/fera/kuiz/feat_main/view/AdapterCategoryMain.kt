package com.fera.kuiz.feat_main.view

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fera.kuiz.BuildConfig
import com.fera.kuiz.common.util.Const
import com.fera.kuiz.databinding.ListItemCategoryBinding
import com.fera.kuiz.feat_CategoryQuestions.view.AddQuestionActivity
import com.fera.kuiz.feat_takeQuiz.model.TblCategory
import com.fera.kuiz.feat_takeQuiz.view.TakeQuizActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdapterCategoryMain(private var listCategory: List<TblCategory>, private val context: Context, private val actions: InterfaceCategoryMain): RecyclerView.Adapter<AdapterCategoryMain.MyViewHolder>() {

    interface InterfaceCategoryMain {
        fun gotoTakeQuizActivity(pkCategoryId: Long, continueQuestion: Boolean)
        fun gotoAddQuestionActivity()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val b = ListItemCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(b)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.b.apply {
            val tblCategory = listCategory[position]

            tblCategory.iconFilePath?.let {
                Glide.with(context)
                    .load(it)
                    .into(sivCategoryIconLiMain)
            }


            tvCategoryLiMain.text = tblCategory.title
            tvCatDescriptionLiMain.text = tblCategory.description

            cpbCatProgressLiMain.max = tblCategory.totalQuestions
            cpbCatProgressLiMain.progress = tblCategory.totalQAnswered

            val progressPercentage = 100 * (tblCategory.totalQAnswered.toFloat() / tblCategory.totalQuestions.toFloat())
            if (progressPercentage == 0f){
                tvProgressPercentLiMain.text = "0 %"
            } else {
                tvProgressPercentLiMain.text = "${progressPercentage.toInt()} %"
            }

            sivCategoryIconLiMain.setOnClickListener {
                actions.gotoAddQuestionActivity()
            }

            root.setOnClickListener {
                actions.gotoTakeQuizActivity(tblCategory.pkCategoryId, false)
            }
        }
    }

    override fun getItemCount(): Int {
       return listCategory.size
    }

    fun updateList(list: List<TblCategory>){
        listCategory = list
        notifyDataSetChanged()
    }


    class MyViewHolder(val b: ListItemCategoryBinding): RecyclerView.ViewHolder(b.root){

    }
}