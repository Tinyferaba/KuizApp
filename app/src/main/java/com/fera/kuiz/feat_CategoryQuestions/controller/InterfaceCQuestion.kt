package com.fera.kuiz.feat_CategoryQuestions.controller

import com.fera.kuiz.feat_CategoryQuestions.model.question.TblQuestion
import com.fera.kuiz.feat_takeQuiz.model.TblCategory

interface InterfaceCQuestion {
    suspend fun getQuestion(pkQuestionId: Long): TblQuestion

    suspend fun getQuestions(pkCategoryId: Long): List<TblQuestion>

    suspend fun getCategory(pkCategoryId: Long): TblCategory
}