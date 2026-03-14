package com.fera.kuiz.feat_CategoryQuestions.model.answer

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.ForeignKey.Companion.NO_ACTION
import androidx.room.Index
import androidx.room.PrimaryKey
import com.fera.kuiz.common.util.DatabaseProperties
import com.fera.kuiz.feat_CategoryQuestions.model.question.TblQuestion

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
    val answer: String
)
