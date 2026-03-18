package com.fera.kuiz.feat_AddQuestion.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fera.kuiz.common.model.database.KuizDb
import com.fera.kuiz.feat_AddQuestion.model.answer.InterfaceAnswer
import com.fera.kuiz.feat_AddQuestion.model.answer.TblAnswer
import com.fera.kuiz.feat_AddQuestion.model.question.InterfaceQuestion
import com.fera.kuiz.feat_AddQuestion.model.question.TblQuestion
import com.fera.kuiz.feat_CategoryQuestions.model.TblCategory

class ControllerAddQuestAct(application: Application): AndroidViewModel(application), InterfaceQuestion, InterfaceAnswer {

    private val daoQuestion = KuizDb.getDatabase(application).daoQuestion()
    private val daoAnswer = KuizDb.getDatabase(application).daoAnswer()
    private val daoCategory = KuizDb.getDatabase(application).daoCategory()

    suspend fun updateCategory(tblCategory: TblCategory){
        daoCategory.updateCategory(tblCategory)
    }

    suspend fun getTotalQuestionsInCategory(pkCategoryId: Long): Int {
        return daoCategory.getTotalQuestionsInCategory(pkCategoryId)
    }

    override suspend fun insertQuestion(tblQuestion: TblQuestion): Long {
        return daoQuestion.insertQuestion(tblQuestion)
    }

    override suspend fun updateQuestion(tblQuestion: TblQuestion) {
        daoQuestion.updateQuestion(tblQuestion)
    }

    override suspend fun deleteQuestion(pkQuestionId: Long) {
        daoQuestion.deleteQuestion(pkQuestionId)
    }

    override suspend fun getQuestion(pkQuestionId: Long): TblQuestion {
        return daoQuestion.getQuestion(pkQuestionId)
    }

    override suspend fun getQuestions(pkCategoryId: Long): List<TblQuestion> {
        return daoQuestion.getQuestions(pkCategoryId)
    }

    override suspend fun getQuestionByCatIdByNo(pkCategoryId: Long, questionNo: Int): TblQuestion {
        return daoQuestion.getQuestionByCatIdByNo(pkCategoryId, questionNo)
    }

    override suspend fun insertAnswer(tblAnswer: TblAnswer): Long {
        return daoAnswer.insertAnswer(tblAnswer)
    }

    override suspend fun updateAnswer(tblAnswer: TblAnswer) {
        daoAnswer.updateAnswer(tblAnswer)
    }

    override suspend fun deleteAnswer(pkAnswerId: Long) {
        daoAnswer.deleteAnswer(pkAnswerId)
    }

    override suspend fun getAnswer(pkAnswerId: Long): TblAnswer {
        return daoAnswer.getAnswer(pkAnswerId)
    }

    override suspend fun getAnswers(pkQuestionId: Long): List<TblAnswer> {
        return daoAnswer.getAnswers(pkQuestionId)
    }


}