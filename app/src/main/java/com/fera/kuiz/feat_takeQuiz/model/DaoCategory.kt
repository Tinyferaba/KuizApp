package com.fera.kuiz.feat_takeQuiz.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update

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
    override suspend fun getCategories(): List<TblCategory>

    @Query("""
                        select *
                        from  tblCategory
                        order by qStartTakenDate desc limit 10
            """)
    override suspend fun getRecentCategories(): List<TblCategory>
}