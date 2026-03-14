package com.fera.kuiz.feat_CategoryQuestions.model.question

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.ForeignKey.Companion.NO_ACTION
import androidx.room.Index
import androidx.room.PrimaryKey
import com.fera.kuiz.common.util.DatabaseProperties
import com.fera.kuiz.feat_takeQuiz.model.TblCategory

@Entity(tableName = DatabaseProperties.TBL_QUESTION,
    foreignKeys = [
        ForeignKey(
            entity = TblCategory::class,
            parentColumns = [DatabaseProperties.TBL_CATEGORY_pkCategoryId],
            childColumns = [DatabaseProperties.TBL_QUESTION_fkQuestion_categoryId],
            onDelete = CASCADE,
            onUpdate = NO_ACTION
        )
    ],
    indices = [Index(value = [DatabaseProperties.TBL_QUESTION_fkQuestion_categoryId])])
data class TblQuestion(
    @PrimaryKey(autoGenerate = false)
    val pkQuestionId: Long,
    val fkQuestion_categoryId: Long,
    val questionNo: Int,
    val questionType: String,
    val question: String,
    val difficulty: Int,
    val dateAdded: Long,
    val dataStatus: Int
)
