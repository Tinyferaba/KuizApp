package com.fera.kuiz.feat_CategoryQuestions.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fera.kuiz.common.model.database.KuizDb
import com.fera.kuiz.feat_CategoryQuestions.model.question.TblQuestion
import com.fera.kuiz.feat_takeQuiz.model.TblCategory

class CategoryController(application: Application): AndroidViewModel(application),  InterfaceCQuestion{

    private val daoQuestion = KuizDb.getDatabase(application).daoQuestion()

    override suspend fun getQuestion(pkQuestionId: Long): TblQuestion {
        return daoQuestion.getQuestion(pkQuestionId)
    }

    override suspend fun getQuestions(pkCategoryId: Long): List<TblQuestion> {
        return daoQuestion.getQuestions(pkCategoryId)
    }

    override suspend fun getCategory(pkCategoryId: Long): TblCategory {
        return daoQuestion.getCategory(pkCategoryId)
    }
}