package com.fera.kuiz.feat_CategoryQuestions.model


import androidx.lifecycle.LiveData
import com.fera.kuiz.feat_AddQuestion.model.question.TblQuestion

interface InterfaceCategory {
    suspend fun insertCategory(tblCategory: TblCategory): Long

    suspend fun updateCategory(tblCategory: TblCategory)

    suspend fun deleteCategory(pkCategoryId: Long)

    suspend fun deleteCategory(tblCategory: TblCategory)

    suspend fun getCategory(pkCategoryId: Long): TblCategory

    fun getCategoryLive(pkCategoryId: Long): LiveData<TblCategory>

    suspend fun getQuestionList(pkCategoryId: Long): List<TblQuestion>

    suspend fun getCategoryIds(): List<Long>

    suspend fun getTotalQuestionsInCategory(pkCategoryId: Long): Int

    suspend fun getTotalQAnswered(pkCategoryId: Long): Int

    suspend fun getTotalCorrectIncorrectAnswers(pkCategoryId: Long, correctOrIncorrect: Int): Int

    suspend fun getLastQTakenQuestionId(pkCategoryId: Long): Long

    suspend fun getLastQTakenQuestionNo(pkCategoryId: Long): Int

    suspend fun resetQuestionsTakenStatus(pkCategoryId: Long)

    suspend fun deleteUserAnswersInCategory(pkCategoryId: Long)

}