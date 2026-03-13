package com.fera.kuiz.common.util

object DatabaseProperties {
    const val TBL_USER_REGISTRATION = "tblUserRegistration"
    const val TBL_USER_REGISTRATION_pkUserRegistrationId = "pkUserRegistrationId"
    const val TBL_USER_REGISTRATION_dateRegistered = "dateRegistered"

    const val TBL_CATEGORY = "tblCategory"
    const val TBL_CATEGORY_pkCategoryId = "pkCategoryId"

    const val TBL_QUESTION = "tblQuestion"
    const val TBL_QUESTION_pkQuestionId = "pkQuestionId"
    const val TBL_QUESTION_fkQuestion_categoryId = "fkQuestion_categoryId"

    const val TBL_ANSWER = "tblAnswer"
    const val TBL_ANSWER_pkAnswerId = "pkAnswerId"
    const val TBL_ANSWER_fkAnswer_questionId = "fkAnswer_questionId"

    const val TBL_USER_ANSWER = "tblUserAnswer"
    const val TBL_USER_ANSWER_pkUserAnswerId = "pkUserAnswerId"
    const val TBL_USER_ANSWER_fkUserAnswer_questionId = "fkUserAnswer_questionId"
    const val TBL_USER_ANSWER_fkUserAnswer_userRegistrationId = "fkUserAnswer_userRegistrationId"

}