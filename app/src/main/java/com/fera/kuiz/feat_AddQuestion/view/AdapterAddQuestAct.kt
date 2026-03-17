package com.fera.kuiz.feat_AddQuestion.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fera.kuiz.databinding.ListItemAddChoiceBinding
import com.fera.kuiz.feat_AddQuestion.model.answer.TblAnswer

class AdapterAddQuestAct(private var listAnswer: List<TblAnswer>, private val context: Context, private val actions: InterfaceAdapterAddQuestAct) : RecyclerView.Adapter<AdapterAddQuestAct.MyViewHolder>() {

    interface InterfaceAdapterAddQuestAct {
        fun deleteAnswer(pkAnswerId: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = ListItemAddChoiceBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tblAnswer = listAnswer[position]

        holder.b.apply {

            edtChoiceAddQues.setText(tblAnswer.answer)

            rbTrueLi.isChecked = tblAnswer.isCorrect
            rbFalseLi.isChecked = !tblAnswer.isCorrect

            ivDeleteChoiceLi.setOnClickListener {
                actions.deleteAnswer(tblAnswer.pkAnswerId)
            }
        }
    }

    override fun getItemCount(): Int {
        return listAnswer.size
    }

    fun updateList(list: List<TblAnswer>){
        listAnswer = list
        notifyDataSetChanged()
    }

    class MyViewHolder(val b: ListItemAddChoiceBinding) : RecyclerView.ViewHolder(b.root)
}

