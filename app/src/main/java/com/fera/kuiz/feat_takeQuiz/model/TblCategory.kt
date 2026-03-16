package com.fera.kuiz.feat_takeQuiz.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fera.kuiz.common.util.DatabaseProperties
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = DatabaseProperties.TBL_CATEGORY)
data class TblCategory(
    @PrimaryKey(autoGenerate = false)
    val pkCategoryId: Long,
    val title: String?,
    val description: String?,
    val note: String?,
    var iconFilePath: String?,
    var noteFilePath: String?,
    var totalQuestions: Int,
    var totalCorrectAnswers: Int,
    var totalWrongAnswers: Int,
    var totalQAnswered: Int,
    var lastQuestionTakenId: Long,
    var lastQuestionTakenNo: Int,
    var qStartTakenDate: Long,
    var dataStatus: Int
) : Parcelable