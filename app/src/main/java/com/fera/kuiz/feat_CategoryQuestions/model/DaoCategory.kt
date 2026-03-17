package com.fera.kuiz.feat_CategoryQuestions.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.fera.kuiz.feat_AddQuestion.model.question.TblQuestion

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
                        select * from  tblCategory
                        where qStartTakenDate > 0
                        order by qStartTakenDate desc limit 10
            """)
     fun getRecentCategories(): LiveData<List<TblCategory>>

    @Query("""select * from tblQuestion where fkQuestion_categoryId = :pkCategoryId""")
    override suspend fun getQuestionList(pkCategoryId: Long): List<TblQuestion>

    @Query("select pkCategoryId from tblCategory")
    override suspend fun getCategoryIds(): List<Long>

    @Query("""
            select count(*) from tblCategory a
            left join tblQuestion b on a.pkCategoryId = fkQuestion_categoryId
            where fkQuestion_categoryId = :pkCategoryId""")
    override suspend fun getTotalQuestionsInCategory(pkCategoryId: Long): Int

    @Query("""
            select count(ua.fkUserAnswer_questionId) from tblcategory c 
                left join tblQuestion q on c.pkCategoryId = q.fkQuestion_categoryId
                left join tbluseranswer ua on q.pkQuestionId = ua.fkuseranswer_questionId
            where c.pkCategoryId = :pkCategoryId
            group by c.pkCategoryId
            """)
    override suspend fun getTotalQAnswered(pkCategoryId: Long): Int

    @Query("""
            select count(ua.fkuseranswer_questionId) from tblcategory c 
                left join tblQuestion q on c.pkCategoryId = q.fkQuestion_categoryId
                left join tbluseranswer ua on q.pkQuestionId = ua.fkuseranswer_questionId
            where c.pkCategoryId = :pkCategoryId and ua.isCorrect = :correctOrIncorrect
            group by c.pkCategoryId
            """)
    override suspend fun getTotalCorrectIncorrectAnswers(pkCategoryId: Long, correctOrIncorrect: Int): Int

    @Query("""
        select q.pkQuestionId from tblcategory c 
            left join tblQuestion q on c.pkCategoryId = q.fkQuestion_categoryId
            left join tbluseranswer ua on q.pkQuestionId = ua.fkuseranswer_questionId
        where c.pkCategoryId = :pkCategoryId
        order by ua.qStartTakenDate desc limit 1
    """)
    override suspend fun getLastQTakenQuestionId(pkCategoryId: Long): Long

    @Query("""
        select q.questionNo from tblcategory c 
            left join tblQuestion q on c.pkCategoryId = q.fkQuestion_categoryId
            left join tbluseranswer ua on q.pkQuestionId = ua.fkuseranswer_questionId
        where c.pkCategoryId = :pkCategoryId
        order by ua.qStartTakenDate desc limit 1
    """)
    override suspend fun getLastQTakenQuestionNo(pkCategoryId: Long): Int

}