package com.fera.kuiz.feat_CategoryQuestions.controller

import androidx.lifecycle.LiveData
import com.fera.kuiz.feat_AddQuestion.model.question.TblQuestion
import com.fera.kuiz.feat_CategoryQuestions.model.TblCategory

interface InterfaceCatQuestion {
    suspend fun getQuestion(pkQuestionId: Long): TblQuestion

    suspend fun getQuestions(pkCategoryId: Long): List<TblQuestion>

    fun getQuestionsLive(pkCategoryId: Long): LiveData<List<TblQuestion>>

    suspend fun getCategory(pkCategoryId: Long): TblCategory
}