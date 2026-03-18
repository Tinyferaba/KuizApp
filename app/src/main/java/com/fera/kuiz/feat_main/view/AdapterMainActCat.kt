package com.fera.kuiz.feat_main.view

import android.animation.ValueAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fera.kuiz.databinding.ListItemCategoryBinding
import com.fera.kuiz.feat_CategoryQuestions.model.TblCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdapterMainActCat(private var listCategory: List<TblCategory>, private val context: Context, private val actions: InterfaceCategoryMain, private val lifecycleOwner: LifecycleOwner): RecyclerView.Adapter<AdapterMainActCat.MyViewHolder>() {

    interface InterfaceCategoryMain {
        fun gotoTakeQuizActivity(pkCategoryId: Long, continueQuestion: Boolean, questionNo: Int)
        fun gotoCategoryActivity(tblCategory: TblCategory)
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

            lifecycleOwner.lifecycleScope.launch {
                delay(500)
                withContext(Dispatchers.Main){
                    tblCategory.let {
                        val maxProgress = it.totalQuestions
                        cpbCatProgressLiMain.max = maxProgress
                        val animatorProgress = ValueAnimator.ofInt(0, it.totalQAnswered)
                        animatorProgress.duration = 1000
                        animatorProgress.addUpdateListener {
                            val value = it.getAnimatedValue() as Int
                            cpbCatProgressLiMain.progress = value
                            tvProgressPercentLiMain.text = "${value}%"
                        }
                        animatorProgress.start()
                    }
                }
            }



            sivCategoryIconLiMain.setOnClickListener {
                actions.gotoCategoryActivity(tblCategory)
            }

            root.setOnClickListener {
                actions.gotoTakeQuizActivity(tblCategory.pkCategoryId, false, tblCategory.lastQuestionTakenNo)
            }
        }

        holder.itemView.translationY = 50F;
        holder.itemView.setAlpha(0f);

        holder.itemView.animate()
            .translationY(0F)
            .alpha(1f)
            .setDuration(300)
            .setStartDelay(position * 50L)
            .start();
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