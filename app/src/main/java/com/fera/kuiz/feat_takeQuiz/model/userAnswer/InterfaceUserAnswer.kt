package com.fera.kuiz.feat_takeQuiz.model.userAnswer

interface InterfaceUserAnswer {
    suspend fun insertUserAnswer(tblUserAnswer: TblUserAnswer): Long

    suspend fun updateUserAnswer(tblUserAnswer: TblUserAnswer)

    suspend fun deleteUserAnswerByQuesIdAnsId(pkQuesId: Long, pkAnsId: Long)

    suspend fun deleteUserAnswerByAnswerId(pkAnswerId: Long)

    suspend fun getAnswersByAnswerId(pkAnswerId: Long): List<TblUserAnswer>

//    suspend fun deleteUserAnswersGreaterThanLastTakenQuestionNo(pkLastTakenQuestId: Long, lastTakenQuestNo: Int)
}