package com.fera.kuiz.feat_CategoryQuestions.model.userAnswer

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

    @Query("delete from tbluseranswer where pkUserAnswerId = :pkUserAnswerId")
    override suspend fun deleteUserAnswer(pkUserAnswerId: Long)
}