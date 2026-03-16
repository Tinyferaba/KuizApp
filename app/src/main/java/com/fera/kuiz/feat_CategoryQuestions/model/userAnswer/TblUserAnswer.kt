package com.fera.kuiz.feat_CategoryQuestions.model.userAnswer

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.ForeignKey.Companion.NO_ACTION
import androidx.room.Index
import androidx.room.PrimaryKey
import com.fera.kuiz.common.util.DatabaseProperties
import com.fera.kuiz.feat_CategoryQuestions.model.answer.TblAnswer
import com.fera.kuiz.feat_CategoryQuestions.model.question.TblQuestion
import com.fera.kuiz.feat_userAuth.model.TblUserRegistration

@Entity(tableName = DatabaseProperties.TBL_USER_ANSWER,
    foreignKeys = [
        ForeignKey(
            entity = TblAnswer::class,
            parentColumns = [DatabaseProperties.TBL_ANSWER_pkAnswerId],
            childColumns = [DatabaseProperties.TBL_USER_ANSWER_fkUserAnswer_AnswerId],
            onDelete = CASCADE,
            onUpdate = NO_ACTION
        )
        , ForeignKey(
            entity = TblQuestion::class,
            parentColumns = [DatabaseProperties.TBL_QUESTION_pkQuestionId],
            childColumns = [DatabaseProperties.TBL_USER_ANSWER_fkUserAnswer_questionId],
            onDelete = CASCADE,
            onUpdate = NO_ACTION
        )
//        , ForeignKey(
//            entity = TblUserRegistration::class,
//            parentColumns = [DatabaseProperties.TBL_USER_REGISTRATION_pkUserRegistrationId],
//            childColumns = [DatabaseProperties.TBL_USER_ANSWER_fkUserAnswer_userRegistrationId],
//            onDelete = CASCADE,
//            onUpdate = NO_ACTION
//        )
    ],
    indices = [
        Index(DatabaseProperties.TBL_USER_ANSWER_fkUserAnswer_AnswerId),
        Index(DatabaseProperties.TBL_USER_ANSWER_fkUserAnswer_questionId)
//        , Index(DatabaseProperties.TBL_USER_ANSWER_fkUserAnswer_userRegistrationId)
    ],
    primaryKeys = [
//        DatabaseProperties.TBL_USER_ANSWER_fkUserAnswer_AnswerId,
        DatabaseProperties.TBL_USER_ANSWER_fkUserAnswer_questionId
    ])
data class TblUserAnswer(
    val fkUserAnswer_AnswerId: Long,
    val fkUserAnswer_questionId: Long,
    val fkUserAnswer_userRegistrationId: Long,
    val answerUser: String,
    val isCorrect: Boolean,
    val qStartTakenDate: Long
)
