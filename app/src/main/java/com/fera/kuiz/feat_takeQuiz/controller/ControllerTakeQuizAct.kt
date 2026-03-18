package com.fera.kuiz.feat_takeQuiz.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fera.kuiz.common.model.database.KuizDb
import com.fera.kuiz.feat_AddQuestion.model.question.HolderCatQuestAndAns
import com.fera.kuiz.feat_AddQuestion.model.question.HolderQuestAndAns
import com.fera.kuiz.feat_AddQuestion.model.question.TblQuestion
import com.fera.kuiz.feat_takeQuiz.model.userAnswer.InterfaceUserAnswer
import com.fera.kuiz.feat_takeQuiz.model.userAnswer.TblUserAnswer
import com.fera.kuiz.feat_CategoryQuestions.model.TblCategory
import com.fera.kuiz.feat_takeQuiz.view.InterfaceTakeQuizAct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ControllerTakeQuizAct(application: Application): AndroidViewModel(application), InterfaceTakeQuizAct, InterfaceUserAnswer {

    private val daoUserAnswer = KuizDb.getDatabase(application).daoUserAnswer()
    private val daoCategory = KuizDb.getDatabase(application).daoCategory()
    private val daoQuestion = KuizDb.getDatabase(application).daoQuestion()
    private val daoAnswer = KuizDb.getDatabase(application).daoAnswer()

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

    suspend fun getTotalQAnswered(pkCategoryId: Long): Int {
        return daoCategory.getTotalQAnswered(pkCategoryId)
    }

    suspend fun getTotalCorrectIncorrectAnswers(pkCategoryId: Long, correctOrIncorrect: Int): Int{
        return daoCategory.getTotalCorrectIncorrectAnswers(pkCategoryId, correctOrIncorrect)
    }

    suspend fun getLastQTakenQuestionId(pkCategoryId: Long): Long {
        return daoCategory.getLastQTakenQuestionId(pkCategoryId)
    }

    suspend fun getLastQTakenQuestionNo(pkCategoryId: Long): Int{
        return daoCategory.getLastQTakenQuestionNo(pkCategoryId)
    }

    suspend fun deleteQuestion(pkQuestionId: Long){
        daoQuestion.deleteQuestion(pkQuestionId)
    }

    fun updateQuestion(tblQuestion: TblQuestion) {
        CoroutineScope(Dispatchers.IO).launch {
            daoQuestion.updateQuestion(tblQuestion)
        }
    }
    override suspend fun insertUserAnswer(tblUserAnswer: TblUserAnswer): Long {
        return daoUserAnswer.insertUserAnswer(tblUserAnswer)
    }

    override suspend fun updateUserAnswer(tblUserAnswer: TblUserAnswer) {
        daoUserAnswer.updateUserAnswer(tblUserAnswer)
    }

    override suspend fun deleteUserAnswerByQuesIdAnsId(pkQuesId: Long, pkAnsId: Long) {
        daoUserAnswer.deleteUserAnswerByQuesIdAnsId(pkQuesId, pkAnsId)
    }

    override suspend fun deleteUserAnswerByAnswerId(pkAnswerId: Long) {
        daoUserAnswer.deleteUserAnswerByAnswerId(pkAnswerId)
    }

    override suspend fun getAnswersByAnswerId(pkAnswerId: Long): List<TblUserAnswer> {
        return daoUserAnswer.getAnswersByAnswerId(pkAnswerId)
    }

    override fun saveUserAnswer(tblUserAnswer: TblUserAnswer) {
        CoroutineScope(Dispatchers.IO).launch {
            insertUserAnswer(tblUserAnswer)
        }
    }

    override fun updateCategoryProperties(tblCategory: TblCategory) {
        CoroutineScope(Dispatchers.IO).launch {
            daoCategory.updateCategory(tblCategory)
        }
    }


}