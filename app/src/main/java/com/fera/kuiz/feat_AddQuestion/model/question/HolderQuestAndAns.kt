package com.fera.kuiz.feat_AddQuestion.model.question

import android.os.Parcelable
import com.fera.kuiz.feat_AddQuestion.model.answer.TblAnswer
import com.fera.kuiz.feat_CategoryQuestions.model.TblCategory
import kotlinx.parcelize.Parcelize

@Parcelize
data class HolderQuestAndAns(
    val tblQuestion: TblQuestion,
    val listAnswers: ArrayList<TblAnswer>
): Parcelable


@Parcelize
data class HolderCatQuestAndAns(
    val tblCategory: TblCategory,
    val listHolderQuestAndAns: ArrayList<HolderQuestAndAns>
): Parcelable