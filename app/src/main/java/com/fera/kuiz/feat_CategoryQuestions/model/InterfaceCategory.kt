package com.fera.kuiz.feat_CategoryQuestions.model


import com.fera.kuiz.feat_AddQuestion.model.question.TblQuestion

interface InterfaceCategory {
    suspend fun insertCategory(tblCategory: TblCategory): Long

    suspend fun updateCategory(tblCategory: TblCategory)

    suspend fun deleteCategory(pkCategoryId: Long)

    suspend fun deleteCategory(tblCategory: TblCategory)

    suspend fun getCategory(pkCategoryId: Long): TblCategory

    suspend fun getQuestionList(pkCategoryId: Long): List<TblQuestion>

    suspend fun getCategoryIds(): List<Long>

    suspend fun getTotalQuestionsInCategory(pkCategoryId: Long): Int
}