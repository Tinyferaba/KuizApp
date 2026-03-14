package com.fera.kuiz.feat_main.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fera.kuiz.BuildConfig
import com.fera.kuiz.R
import com.fera.kuiz.common.util.Const
import com.fera.kuiz.databinding.ListItemCategoryBinding
import com.fera.kuiz.databinding.ListItemRecentCatBinding
import com.fera.kuiz.feat_Questions.model.question.HolderQuesAnsAndUserAns
import com.fera.kuiz.feat_Questions.view.AddQuestionActivity
import com.fera.kuiz.feat_takeQuiz.model.TblCategory
import com.fera.kuiz.feat_takeQuiz.view.TakeQuizActivity
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdapterCategory(private var listCategory: List<TblCategory>, private val context: Context, private val actions: AdapterCategoryActions): RecyclerView.Adapter<AdapterCategory.MyViewHolder>() {


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
                    .into(sivCatIcon)
            }

            tvCategoryLiMain.text = tblCategory.title
            tvCatDescriptionLiMain.text = tblCategory.description

            cpbCatProgressLiMain.max = tblCategory.totalQuestions
            cpbCatProgressLiMain.progress = tblCategory.totalQAnswered

            sivCatIcon.setOnClickListener {
                val intent = Intent(context, AddQuestionActivity::class.java)
                intent.putExtra(Const.ACTIVITY_KEY, BuildConfig.ACTIVITY_PASSWORD)
                context.startActivity(intent)
            }

            root.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val holderQAAndUA = actions.getQuestion(tblCategory.lastQuestionTakenId)

                    withContext(Dispatchers.IO){
                        val intent = Intent(context, TakeQuizActivity::class.java)
                        intent.putExtra(Const.CATEGORY, tblCategory)
                        intent.putExtra(Const.QuestionAnswerAndUserAnswer, holderQAAndUA)
                        intent.putExtra(Const.ACTIVITY_KEY, BuildConfig.ACTIVITY_PASSWORD)
                        context.startActivity(intent)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
       return listCategory.size
    }

    fun updateList(list: List<TblCategory>){
        listCategory = list
    }


    class MyViewHolder(val b: ListItemCategoryBinding): RecyclerView.ViewHolder(b.root){

    }
}