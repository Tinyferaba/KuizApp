package com.fera.kuiz.feat_main.view

import android.animation.ValueAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fera.kuiz.databinding.ListItemRecentCatBinding
import com.fera.kuiz.feat_CategoryQuestions.model.TblCategory
import kotlin.math.roundToInt

class AdapterMainActRecentCat(private var listRecentCat: List<TblCategory>, private val context: Context, private val actions: InterfaceAdapterRecentCat): RecyclerView.Adapter<AdapterMainActRecentCat.MyViewHolder>() {

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

            tblCategory.let {
                val maxCorrect = it.totalQAnswered
                pbCorrectLiRecent.max = maxCorrect
                val animatorCorrect = ValueAnimator.ofInt(0, it.totalCorrectAnswers)
                animatorCorrect.duration = 1000
                animatorCorrect.addUpdateListener {
                    val value = it.getAnimatedValue() as Int
                    pbCorrectLiRecent.progress = value
                }
                animatorCorrect.start()

                val maxProgress = it.totalQuestions
                pbProgressLiRecent.max = maxProgress
                val animatorProgress = ValueAnimator.ofInt(0, it.totalQAnswered)
                animatorProgress.duration = 1000
                animatorProgress.addUpdateListener {
                    val value = it.getAnimatedValue() as Int
                    pbProgressLiRecent.progress = value
                }
                animatorProgress.start()
            }

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