package com.fera.kuiz.feat_Questions.model.userAnswer

interface InterfaceUserAnswer {
    suspend fun insertUserAnswer(tblUserAnswer: TblUserAnswer): Long

    suspend fun updateUserAnswer(tblUserAnswer: TblUserAnswer)

    suspend fun deleteUserAnswer(pkUserAnswerId: Long)
}