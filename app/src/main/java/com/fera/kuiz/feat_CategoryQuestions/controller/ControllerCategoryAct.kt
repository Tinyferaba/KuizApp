package com.fera.kuiz.feat_CategoryQuestions.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fera.kuiz.common.model.database.KuizDb
import com.fera.kuiz.feat_AddQuestion.model.answer.TblAnswer
import com.fera.kuiz.feat_AddQuestion.model.question.HolderCatQuestAndAns
import com.fera.kuiz.feat_AddQuestion.model.question.HolderQuestAndAns
import com.fera.kuiz.feat_AddQuestion.model.question.TblQuestion
import com.fera.kuiz.feat_CategoryQuestions.model.InterfaceCategory
import com.fera.kuiz.feat_CategoryQuestions.model.TblCategory

class ControllerCategoryAct(application: Application): AndroidViewModel(application), InterfaceCategory,  InterfaceCatQuestion{

    private val daoQuestion = KuizDb.getDatabase(application).daoQuestion()
    private val daoCategory = KuizDb.getDatabase(application).daoCategory()
    private val daoAnswer = KuizDb.getDatabase(application).daoAnswer()

    suspend fun getAnswers(pkQuestionId: Long): List<TblAnswer> {
        return daoAnswer.getAnswers(pkQuestionId)
    }

    override suspend fun getQuestion(pkQuestionId: Long): TblQuestion {
        return daoQuestion.getQuestion(pkQuestionId)
    }

    override suspend fun getQuestions(pkCategoryId: Long): List<TblQuestion> {
        return daoQuestion.getQuestions(pkCategoryId)
    }

    override fun getQuestionsLive(pkCategoryId: Long): LiveData<List<TblQuestion>> {
        return daoQuestion.getQuestionsLive(pkCategoryId)
    }

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
        return daoQuestion.getCategory(pkCategoryId)
    }

    override suspend fun getQuestionList(pkCategoryId: Long): List<TblQuestion> {
        return daoCategory.getQuestionList(pkCategoryId)
    }

    override suspend fun getCategoryIds(): List<Long> {
        return daoCategory.getCategoryIds()
    }

    override suspend fun getTotalQuestionsInCategory(pkCategoryId: Long): Int {
        return daoCategory.getTotalQuestionsInCategory(pkCategoryId)
    }

    override suspend fun getTotalQAnswered(pkCategoryId: Long): Int {
        return daoCategory.getTotalQAnswered(pkCategoryId)
    }

    override suspend fun getTotalCorrectIncorrectAnswers(pkCategoryId: Long, correctOrIncorrect: Int): Int {
        return daoCategory.getTotalCorrectIncorrectAnswers(pkCategoryId, correctOrIncorrect)
    }

    override suspend fun getLastQTakenQuestionId(pkCategoryId: Long): Long {
        return daoCategory.getLastQTakenQuestionId(pkCategoryId)
    }

    override suspend fun getLastQTakenQuestionNo(pkCategoryId: Long): Int {
        return daoCategory.getLastQTakenQuestionNo(pkCategoryId)
    }

    override suspend fun resetQuestionsTakenStatus(pkCategoryId: Long) {
        daoCategory.resetQuestionsTakenStatus(pkCategoryId)
    }

    override suspend fun deleteUserAnswersInCategory(pkCategoryId: Long) {
        daoCategory.deleteUserAnswersInCategory(pkCategoryId)
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