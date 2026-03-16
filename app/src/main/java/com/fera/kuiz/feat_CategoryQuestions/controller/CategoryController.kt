package com.fera.kuiz.feat_CategoryQuestions.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fera.kuiz.common.model.database.KuizDb
import com.fera.kuiz.feat_CategoryQuestions.model.question.HolderCatQuestAndAns
import com.fera.kuiz.feat_CategoryQuestions.model.question.HolderQuestAndAns
import com.fera.kuiz.feat_CategoryQuestions.model.question.TblQuestion
import com.fera.kuiz.feat_takeQuiz.model.TblCategory

class CategoryController(application: Application): AndroidViewModel(application),  InterfaceCQuestion{

    private val daoQuestion = KuizDb.getDatabase(application).daoQuestion()
    private val daoCategory = KuizDb.getDatabase(application).daoCategory()
    private val daoAnswer = KuizDb.getDatabase(application).daoAnswer()

    override suspend fun getQuestion(pkQuestionId: Long): TblQuestion {
        return daoQuestion.getQuestion(pkQuestionId)
    }

    override suspend fun getQuestions(pkCategoryId: Long): List<TblQuestion> {
        return daoQuestion.getQuestions(pkCategoryId)
    }

    override suspend fun getCategory(pkCategoryId: Long): TblCategory {
        return daoQuestion.getCategory(pkCategoryId)
    }


    suspend fun getHolderCatQuestAndAns(pkCategoryId: Long): HolderCatQuestAndAns {
        val listHolderQuestAndAns = arrayListOf<HolderQuestAndAns>()

        val tblCategory = daoCategory.getCategory(pkCategoryId)
        val listQuestions = daoQuestion.getQuestions(pkCategoryId)

        listQuestions.forEach { tblQuestion ->
            val listAnswers = daoAnswer.getAnswers(tblQuestion.pkQuestionId)
            listHolderQuestAndAns.add(HolderQuestAndAns(tblQuestion, ArrayList(listAnswers)))
        }

        return HolderCatQuestAndAns(tblCategory, listHolderQuestAndAns)
    }
}