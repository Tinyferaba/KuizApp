package com.fera.kuiz.feat_CategoryQuestions.model.answer

interface InterfaceAnswer {
    suspend fun insertAnswer(tblAnswer: TblAnswer): Long

    suspend fun updateAnswer(tblAnswer: TblAnswer)

    suspend fun deleteAnswer(pkAnswerId: Long)

    suspend fun getAnswer(pkAnswerId: Long): TblAnswer

    suspend fun getAnswers(pkQuestionId: Long): List<TblAnswer>
}