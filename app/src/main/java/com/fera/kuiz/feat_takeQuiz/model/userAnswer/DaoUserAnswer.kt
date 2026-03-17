package com.fera.kuiz.feat_takeQuiz.model.userAnswer

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query

@Dao
interface DaoUserAnswer : InterfaceUserAnswer{
    @Insert(onConflict = REPLACE)
    override suspend fun insertUserAnswer(tblUserAnswer: TblUserAnswer): Long

    @Insert(onConflict = REPLACE)
    override suspend fun updateUserAnswer(tblUserAnswer: TblUserAnswer)

    @Query("delete from tbluseranswer where fkUserAnswer_questionId = :pkQuesId and fkUserAnswer_AnswerId = :pkAnsId")
    override suspend fun deleteUserAnswerByQuesIdAnsId(pkQuesId: Long, pkAnsId: Long)

    @Query("delete from tbluseranswer where fkUserAnswer_AnswerId = :pkAnswerId")
    override suspend fun deleteUserAnswerByAnswerId(pkAnswerId: Long)

    @Query("select * from tbluseranswer where fkUserAnswer_AnswerId = :pkAnswerId")
    override suspend fun getAnswersByAnswerId(pkAnswerId: Long): List<TblUserAnswer>

//    @Query("""delete from tblUserAnswer where fkUserAnswer_questionId = :pkLastTakenQuestId and """)
//    override suspend fun deleteUserAnswersGreaterThanLastTakenQuestionNo(pkLastTakenQuestId: Long, lastTakenQuestNo: Int)
}