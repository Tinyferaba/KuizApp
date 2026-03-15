package com.fera.kuiz.feat_CategoryQuestions.model.question

import android.os.Parcelable
import com.fera.kuiz.feat_CategoryQuestions.model.answer.TblAnswer
import com.fera.kuiz.feat_takeQuiz.model.TblCategory
import kotlinx.parcelize.Parcelize

@Parcelize
data class HolderQuestAndAns(
    val tblQuestion: TblQuestion,
    val tblAnswer: ArrayList<TblAnswer>
): Parcelable


@Parcelize
data class HolderCatQuestAndAns(
    val tblCategory: TblCategory,
    val holderQuestAndAns: ArrayList<HolderQuestAndAns>
): Parcelable