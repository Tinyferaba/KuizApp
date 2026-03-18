package com.fera.kuiz.feat_AddQuestion.model.question

interface InterfaceQuestion {
    suspend fun insertQuestion(tblQuestion: TblQuestion): Long

    suspend fun updateQuestion(tblQuestion: TblQuestion)

    suspend fun deleteQuestion(pkQuestionId: Long)

    suspend fun getQuestion(pkQuestionId: Long): TblQuestion

    suspend fun getQuestions(pkCategoryId: Long): List<TblQuestion>

    suspend fun getQuestionByCatIdByNo(pkCategoryId: Long, questionNo: Int): TblQuestion
}