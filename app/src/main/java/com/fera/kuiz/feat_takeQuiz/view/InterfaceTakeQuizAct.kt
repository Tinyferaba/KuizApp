package com.fera.kuiz.feat_takeQuiz.view

import com.fera.kuiz.feat_CategoryQuestions.model.userAnswer.TblUserAnswer
import com.fera.kuiz.feat_takeQuiz.model.TblCategory

interface InterfaceTakeQuizAct {
    fun saveUserAnswer(tblUserAnswer: TblUserAnswer)
    fun updateCategoryProperties(tblCategory: TblCategory)
}