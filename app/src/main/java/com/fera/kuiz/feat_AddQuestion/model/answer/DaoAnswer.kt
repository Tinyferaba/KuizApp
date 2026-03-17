package com.fera.kuiz.feat_AddQuestion.model.answer

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update

@Dao
interface DaoAnswer : InterfaceAnswer {
    @Insert(onConflict = REPLACE)
    override suspend fun insertAnswer(tblAnswer: TblAnswer): Long

    @Update(onConflict = REPLACE)
    override suspend fun updateAnswer(tblAnswer: TblAnswer)

    @Query("delete from tblanswer where pkAnswerId = :pkAnswerId")
    override suspend fun deleteAnswer(pkAnswerId: Long)

    @Query("select * from tblAnswer where pkAnswerId = :pkAnswerId")
    override suspend fun getAnswer(pkAnswerId: Long): TblAnswer

    @Query("select * from tblAnswer where fkAnswer_questionId = :pkQuestionId")
    override suspend fun getAnswers(pkQuestionId: Long): List<TblAnswer>
}