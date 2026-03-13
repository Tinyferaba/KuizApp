package com.fera.kuiz.feat_takeQuiz.model

interface InterfaceCategory {
    suspend fun insertCategory(tblCategory: TblCategory): Long

    suspend fun updateCategory(tblCategory: TblCategory)

    suspend fun deleteCategory(pkCategoryId: Long)

    suspend fun deleteCategory(tblCategory: TblCategory)

    suspend fun getCategory(pkCategoryId: Long): TblCategory

    suspend fun getCategories(): List<TblCategory>

    suspend fun getRecentCategories(): List<TblCategory>
}