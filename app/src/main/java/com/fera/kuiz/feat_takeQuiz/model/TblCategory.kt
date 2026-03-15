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
    val iconFilePath: String?,
    val noteFilePath: String?,
    val totalQuestions: Int,
    val totalCorrectAnswers: Int,
    val totalWrongAnswers: Int,
    val totalQAnswered: Int,
    val lastQuestionTakenId: Long,
    val lastQuestionTakenNo: Int,
    val qStartTakenDate: Long,
    val dataStatus: Int
) : Parcelable