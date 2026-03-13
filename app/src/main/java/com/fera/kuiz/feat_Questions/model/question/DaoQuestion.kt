package com.fera.kuiz.feat_Questions.model.question

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update

@Dao
interface DaoQuestion : InterfaceQuestion {
    @Insert(onConflict = REPLACE)
    override suspend fun insertQuestion(tblQuestion: TblQuestion): Long

    @Update(onConflict = REPLACE)
    override suspend fun updateQuestion(tblQuestion: TblQuestion)

    @Query("delete from tblQuestion where pkQuestionId = :pkQuestionId")
    override suspend fun deleteQuestion(pkQuestionId: Long)
}