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

class AdapterRecentCat(private var listRecentCat: List<TblCategory>, private val context: Context, private val actions: AdapterRecentCatActions): RecyclerView.Adapter<AdapterRecentCat.MyViewHolder>() {
    interface AdapterRecentCatActions {
        suspend fun getQuestion(pkCategoryId: Long):
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
                val intent = Intent(context, TakeQuizActivity::class.java)
                intent.putExtra(Const.CATEGORY, tblCategory)
                intent.putExtra(Const.ACTIVITY_KEY, BuildConfig.ACTIVITY_PASSWORD)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
       return listRecentCat.size
    }

    fun updateList(list: List<TblCategory>){
        listRecentCat = list
    }


    class MyViewHolder(val b: ListItemRecentCatBinding): RecyclerView.ViewHolder(b.root){

    }
}