package com.fera.kuiz.feat_Questions.model.answer

interface InterfaceAnswer {
    suspend fun insertAnswer(tblAnswer: TblAnswer): Long

    suspend fun updateAnswer(tblAnswer: TblAnswer)

    suspend fun deleteAnswer(pkAnswerId: Long)
}