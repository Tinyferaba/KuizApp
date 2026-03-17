package com.fera.kuiz.feat_AddQuestion.model.question

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.ForeignKey.Companion.NO_ACTION
import androidx.room.Index
import androidx.room.PrimaryKey
import com.fera.hymn.utils.enumeration.EnumDataInfo
import com.fera.kuiz.common.util.DatabaseProperties
import com.fera.kuiz.feat_CategoryQuestions.model.TblCategory
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
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
    var fkQuestion_categoryId: Long = 0L,
    var questionNo: Int = -1,
    var questionType: String = "",
    var question: String = "",
    var difficulty: Int = 1,
    var isTaken: Boolean = false,
    var dateAdded: Long = Date().time,
    var dataStatus: Int = EnumDataInfo.NONE.info
) : Parcelable
