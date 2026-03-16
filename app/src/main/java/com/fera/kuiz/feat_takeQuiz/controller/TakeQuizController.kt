package com.fera.kuiz.feat_takeQuiz.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fera.kuiz.common.model.database.KuizDb
import com.fera.kuiz.feat_CategoryQuestions.model.userAnswer.InterfaceUserAnswer
import com.fera.kuiz.feat_CategoryQuestions.model.userAnswer.TblUserAnswer
import com.fera.kuiz.feat_takeQuiz.model.TblCategory
import com.fera.kuiz.feat_takeQuiz.view.InterfaceTakeQuizAct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TakeQuizController(application: Application): AndroidViewModel(application), InterfaceTakeQuizAct, InterfaceUserAnswer {

    private val daoUserAnswer = KuizDb.getDatabase(application).daoUserAnswer()
    private val daoCategory = KuizDb.getDatabase(application).daoCategory()

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