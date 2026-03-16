package com.fera.kuiz.feat_main.view

import com.fera.kuiz.feat_CategoryQuestions.model.question.HolderCatQuestAndAns
import com.fera.kuiz.feat_CategoryQuestions.model.question.HolderQuesAnsAndUserAns

interface InterfaceMainAct {
    suspend fun getQuestion(pkLastQuestionTakenId: Long): HolderQuesAnsAndUserAns
    suspend fun getQuestionInCategory(pkCategoryId: Long): List<HolderQuesAnsAndUserAns>
    suspend fun getHolderCatQuestAndAns(pkCategoryId: Long): HolderCatQuestAndAns
}