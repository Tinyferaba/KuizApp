package com.fera.kuiz.feat_takeQuiz.model

import com.fera.kuiz.feat_CategoryQuestions.model.question.HolderQuestAndAns
import com.fera.kuiz.feat_CategoryQuestions.model.question.HolderQuesAnsAndUserAns

interface InterfaceCategory {
    suspend fun insertCategory(tblCategory: TblCategory): Long

    suspend fun updateCategory(tblCategory: TblCategory)

    suspend fun deleteCategory(pkCategoryId: Long)

    suspend fun deleteCategory(tblCategory: TblCategory)

    suspend fun getCategory(pkCategoryId: Long): TblCategory

    suspend fun getQuestionAnswerAndUserAnswer(pkLastQuestionTakenId: Long): HolderQuesAnsAndUserAns

    suspend fun getQuestionList(pkCategoryId: Long): List<HolderQuesAnsAndUserAns>

}