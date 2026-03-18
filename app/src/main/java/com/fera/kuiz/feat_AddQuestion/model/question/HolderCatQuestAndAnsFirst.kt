package com.fera.kuiz.feat_AddQuestion.model.question

import android.os.Parcelable
import com.fera.kuiz.feat_AddQuestion.model.answer.TblAnswer
import com.fera.kuiz.feat_CategoryQuestions.model.TblCategory
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HolderCatQuestAndAnsFirst(
    val tblCategory: TblCategory,
    val tblQuestion: TblQuestion,
    val listAnswers: ArrayList<TblAnswer>
) : Parcelable
