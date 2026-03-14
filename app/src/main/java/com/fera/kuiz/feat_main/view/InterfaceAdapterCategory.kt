package com.fera.kuiz.feat_main.view

import com.fera.kuiz.feat_CategoryQuestions.model.question.HolderQuesAnsAndUserAns

interface InterfaceAdapterCategory {
    suspend fun getQuestion(pkLastQuestionTakenId: Long): HolderQuesAnsAndUserAns
}