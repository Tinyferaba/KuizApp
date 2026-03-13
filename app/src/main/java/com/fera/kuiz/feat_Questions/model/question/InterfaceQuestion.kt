package com.fera.kuiz.feat_Questions.model.question

interface InterfaceQuestion {
    suspend fun insertQuestion(tblQuestion: TblQuestion): Long

    suspend fun updateQuestion(tblQuestion: TblQuestion)

    suspend fun deleteQuestion(pkQuestionId: Long)
}