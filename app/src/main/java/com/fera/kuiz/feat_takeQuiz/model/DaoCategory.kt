package com.fera.kuiz.feat_takeQuiz.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.fera.kuiz.feat_CategoryQuestions.model.question.HolderQuesAnsAndUserAns

@Dao
interface DaoCategory : InterfaceCategory {
    @Insert(onConflict = REPLACE)
    override suspend fun insertCategory(tblCategory: TblCategory): Long

    @Update(onConflict = REPLACE)
    override suspend fun updateCategory(tblCategory: TblCategory)

    @Query("delete from tblcategory where pkCategoryId = :pkCategoryId")
    override suspend fun deleteCategory(pkCategoryId: Long)

    @Delete
    override suspend fun deleteCategory(tblCategory: TblCategory)

    @Query("select * from tblcategory where pkCategoryId = :pkCategoryId")
    override suspend fun getCategory(pkCategoryId: Long): TblCategory

    @Query("select * from tblcategory")
     fun getCategories(): LiveData<List<TblCategory>>

    @Query("""
                        select *
                        from  tblCategory
                        order by qStartTakenDate desc limit 10
            """)
     fun getRecentCategories(): LiveData<List<TblCategory>>

    @Query(
        """
            select 
                c.pkCategoryId,
                c.iconFilePath,
                c.noteFilePath,
                c.totalQuestions,
                c.lastQuestionTakenId,
                c.lastQuestionTakenNo,
                
                q.pkQuestionId,
                q.questionNo,
                q.questionType,
                q.question,
                q.difficulty,
                q.dateAdded,
                q.dataStatus,
                
                a.pkAnswerId,
                a.answer,
                
                ua.pkUserAnswerId,
                ua.answerUser,
                ua.isCorrect,
                ua.qStartTakenDate
            from tblCategory c 
                left join tblquestion q on c.pkCategoryId = q.fkQuestion_categoryId
                left join tblanswer a on q.pkQuestionId = a.fkAnswer_questionId
                left join tbluseranswer ua on q.pkQuestionId = ua.fkUserAnswer_questionId
            where q.pkQuestionId = :pkLastQuestionTakenId
        """
    )
    override suspend fun getQuestionAnswerAndUserAnswer(pkLastQuestionTakenId: Long): HolderQuesAnsAndUserAns

    @Query("""select 
                c.pkCategoryId,
                c.iconFilePath,
                c.noteFilePath,
                c.totalQuestions,
                c.lastQuestionTakenId,
                c.lastQuestionTakenNo,
                
                q.pkQuestionId,
                q.questionNo,
                q.questionType,
                q.question,
                q.difficulty,
                q.dateAdded,
                q.dataStatus,
                
                a.pkAnswerId,
                a.answer,
                
                ua.pkUserAnswerId,
                ua.answerUser,
                ua.isCorrect,
                ua.qStartTakenDate
            from tblCategory c 
                left join tblquestion q on c.pkCategoryId = q.fkQuestion_categoryId
                left join tblanswer a on q.pkQuestionId = a.fkAnswer_questionId
                left join tbluseranswer ua on q.pkQuestionId = ua.fkUserAnswer_questionId
            where c.pkCategoryId = :pkCategoryId
                """)
    override suspend fun getQuestionList(pkCategoryId: Long): List<HolderQuesAnsAndUserAns>



}