package com.fera.kuiz.feat_main.view

import com.fera.kuiz.feat_CategoryQuestions.model.question.HolderCatQuestAndAns
import com.fera.kuiz.feat_CategoryQuestions.model.question.HolderQuestAndAns
import com.fera.kuiz.feat_CategoryQuestions.model.question.HolderQuesAnsAndUserAns

interface InterfaceAdapterCategory {
    suspend fun getQuestion(pkLastQuestionTakenId: Long): HolderQuesAnsAndUserAns
    suspend fun getQuestionInCategory(pkCategoryId: Long): List<HolderQuesAnsAndUserAns>
    fun gotoAddQuestionActivity()
    fun gotoTakeQuizActivity(pkCategoryId: Long)
    suspend fun getHolderCatQuestAndAns(pkCategoryId: Long): ArrayList<HolderCatQuestAndAns>
}


