package com.fera.kuiz.feat_AddQuestion.model.answer

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.ForeignKey.Companion.NO_ACTION
import androidx.room.Index
import androidx.room.PrimaryKey
import com.fera.kuiz.common.util.DatabaseProperties
import com.fera.kuiz.feat_AddQuestion.model.question.TblQuestion
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = DatabaseProperties.TBL_ANSWER,
    foreignKeys = [
        ForeignKey(
            entity = TblQuestion::class,
            parentColumns = [DatabaseProperties.TBL_QUESTION_pkQuestionId],
            childColumns = [DatabaseProperties.TBL_ANSWER_fkAnswer_questionId],
            onUpdate = NO_ACTION,
            onDelete = CASCADE
        )
    ],
    indices = [Index(DatabaseProperties.TBL_ANSWER_fkAnswer_questionId)])
data class TblAnswer(
    @PrimaryKey(autoGenerate = false)
    val pkAnswerId: Long,
    val fkAnswer_questionId: Long,
    var answer: String = "",
    var description: String = "",
    var isCorrect: Boolean = false
) : Parcelable
