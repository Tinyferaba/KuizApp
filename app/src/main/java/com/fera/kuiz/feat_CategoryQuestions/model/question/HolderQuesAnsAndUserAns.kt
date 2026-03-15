package com.fera.kuiz.feat_CategoryQuestions.model.question

import android.os.Parcelable
import com.fera.hymn.utils.enumeration.EnumDataInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class HolderQuesAnsAndUserAns(
    var pkCategoryId: Long = 0L,
    val iconFilePath: String? = null,
    val noteFilePath: String? = null,
    val totalQuestions: Int = 0,
    val lastQuestionTakenId: Long = 0L,
    val lastQuestionTakenNo: Int = EnumDataInfo.NONE.info,

    var pkQuestionId: Long = 0L,
    var questionNo: Int = 0,
    var questionType: String? = "",
    var question: String? = "",
    var difficulty: Int = 0,
    var dateAdded: Long = 0L,
    var dataStatus: Int = EnumDataInfo.NONE.info,

    var pkAnswerId: Long = 0L,
    var answer: String? = "",

    var pkUserAnswerId: Long = 0L,
    var answerUser: String? = "",
    var isCorrect: Boolean = false,
    var qStartTakenDate: Long = 0L
) : Parcelable