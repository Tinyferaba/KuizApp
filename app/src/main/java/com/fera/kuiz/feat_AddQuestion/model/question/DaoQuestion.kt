package com.fera.kuiz.feat_AddQuestion.model.question

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.fera.kuiz.feat_CategoryQuestions.controller.InterfaceCatQuestion
import com.fera.kuiz.feat_CategoryQuestions.model.TblCategory

@Dao
interface DaoQuestion : InterfaceQuestion, InterfaceCatQuestion {
    @Insert(onConflict = REPLACE)
    override suspend fun insertQuestion(tblQuestion: TblQuestion): Long

    @Update(onConflict = REPLACE)
    override suspend fun updateQuestion(tblQuestion: TblQuestion)

    @Query("delete from tblQuestion where pkQuestionId = :pkQuestionId")
    override suspend fun deleteQuestion(pkQuestionId: Long)

    @Query("select * from tblquestion where pkQuestionId = :pkQuestionId")
    override suspend fun getQuestion(pkQuestionId: Long): TblQuestion

    @Query("select * from tblQuestion where fkQuestion_categoryId = :pkCategoryId order by questionNo asc")
    override suspend fun getQuestions(pkCategoryId: Long): List<TblQuestion>

    @Query("select * from tblQuestion where fkQuestion_categoryId = :pkCategoryId")
    override fun getQuestionsLive(pkCategoryId: Long): LiveData<List<TblQuestion>>

    @Query("select * from tblCategory where pkCategoryId = :pkCategoryId")
    override suspend fun getCategory(pkCategoryId: Long): TblCategory

    @Query("""
        select 
            q.pkQuestionId,
            q.fkQuestion_categoryId,
            q.questionNo,
            q.questionType,
            q.question,
            q.difficulty,
            q.isTaken,
            q.dateAdded,
            q.dataStatus
        from tblQuestion q 
            left join tblCategory c on q.fkQuestion_categoryId = c.pkCategoryId
        where pkCategoryId = :pkCategoryId and questionNo = :questionNo
        order by q.questionNo asc
    """)
    override suspend fun getQuestionByCatIdByNo(pkCategoryId: Long, questionNo: Int): TblQuestion
}