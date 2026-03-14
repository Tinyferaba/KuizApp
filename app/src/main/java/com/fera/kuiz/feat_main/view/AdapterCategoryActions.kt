package com.fera.kuiz.feat_main.view

import com.fera.kuiz.feat_Questions.model.question.HolderQuesAnsAndUserAns

interface AdapterCategoryActions {
    suspend fun getQuestion(pkLastQuestionTakenId: Long): HolderQuesAnsAndUserAns
}