package com.fera.kuiz.feat_main.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fera.kuiz.common.model.database.KuizDb
import com.fera.kuiz.feat_takeQuiz.model.InterfaceCategory
import com.fera.kuiz.feat_takeQuiz.model.TblCategory

class MainController(application: Application) : AndroidViewModel(application), InterfaceCategory {

    private val daoCategory = KuizDb.getDatabase(application).daoCategory()

    override suspend fun insertCategory(tblCategory: TblCategory): Long {
        return daoCategory.insertCategory(tblCategory)
    }

    override suspend fun updateCategory(tblCategory: TblCategory) {
        daoCategory.updateCategory(tblCategory)
    }

    override suspend fun deleteCategory(pkCategoryId: Long) {
        daoCategory.deleteCategory(pkCategoryId)
    }

    override suspend fun deleteCategory(tblCategory: TblCategory) {
        daoCategory.deleteCategory(tblCategory)
    }

    override suspend fun getCategory(pkCategoryId: Long): TblCategory {
        return daoCategory.getCategory(pkCategoryId)
    }

    override suspend fun getCategories(): List<TblCategory> {
        return daoCategory.getCategories()
    }

    override suspend fun getRecentCategories(): List<TblCategory> {
        return daoCategory.getRecentCategories()
    }

}