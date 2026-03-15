package com.fera.kuiz.common.util

import android.content.Context
import android.widget.Toast
import com.fera.kuiz.feat_main.controller.MainController
import com.fera.kuiz.feat_takeQuiz.model.TblCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun Context.toastShort(msg: String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT)
}

fun Context.toastLong(msg: String){
    Toast.makeText(this, msg, Toast.LENGTH_LONG)
}

fun generatePrimaryKey(): Long {
    val timestamp = System.currentTimeMillis()
    val randomSuffix = (0..500).random() // Generate a random number between 0 and 99
    return (timestamp * 100) + randomSuffix
}


fun loadData(controller: MainController, context: Context){
    insertCategories(controller, context)
}




fun insertCategories(controller: MainController, context: Context){
    val categories = listOf(
        TblCategory(
            pkCategoryId = generatePrimaryKey(),
            title = "General Knowledge",
            description = "Basic questions covering a wide range of common knowledge topics",
            note = null,
            iconFilePath = null,
            noteFilePath = null,
            totalQuestions = 50,
            totalCorrectAnswers = 0,
            totalWrongAnswers = 0,
            totalQAnswered = 0,
            lastQuestionTakenId = 0,
            lastQuestionTakenNo = 0,
            qStartTakenDate = 0,
            dataStatus = 0
        ),

        TblCategory(
            pkCategoryId = generatePrimaryKey(),
            title = "Science",
            description = "Questions related to physics, chemistry, biology, and scientific discoveries",
            note = null,
            iconFilePath = null,
            noteFilePath = null,
            totalQuestions = 50,
            totalCorrectAnswers = 0,
            totalWrongAnswers = 0,
            totalQAnswered = 0,
            lastQuestionTakenId = 0,
            lastQuestionTakenNo = 0,
            qStartTakenDate = 0,
            dataStatus = 0
        ),

        TblCategory(
            pkCategoryId = generatePrimaryKey(),
            title = "Mathematics",
            description = "Arithmetic, algebra, geometry, and logical math problems",
            note = null,
            iconFilePath = null,
            noteFilePath = null,
            totalQuestions = 50,
            totalCorrectAnswers = 0,
            totalWrongAnswers = 0,
            totalQAnswered = 0,
            lastQuestionTakenId = 0,
            lastQuestionTakenNo = 0,
            qStartTakenDate = 0,
            dataStatus = 0
        ),

        TblCategory(
            pkCategoryId = generatePrimaryKey(),
            title = "History",
            description = "Questions about historical events, civilizations, and famous figures",
            note = null,
            iconFilePath = null,
            noteFilePath = null,
            totalQuestions = 50,
            totalCorrectAnswers = 0,
            totalWrongAnswers = 0,
            totalQAnswered = 0,
            lastQuestionTakenId = 0,
            lastQuestionTakenNo = 0,
            qStartTakenDate = 0,
            dataStatus = 0
        ),

        TblCategory(
            pkCategoryId = generatePrimaryKey(),
            title = "Geography",
            description = "Countries, capitals, landscapes, maps, and world geography",
            note = null,
            iconFilePath = null,
            noteFilePath = null,
            totalQuestions = 50,
            totalCorrectAnswers = 0,
            totalWrongAnswers = 0,
            totalQAnswered = 0,
            lastQuestionTakenId = 0,
            lastQuestionTakenNo = 0,
            qStartTakenDate = 0,
            dataStatus = 0
        ),

        TblCategory(
            pkCategoryId = generatePrimaryKey(),
            title = "Technology",
            description = "Computers, internet, software, and modern technology topics",
            note = null,
            iconFilePath = null,
            noteFilePath = null,
            totalQuestions = 50,
            totalCorrectAnswers = 0,
            totalWrongAnswers = 0,
            totalQAnswered = 0,
            lastQuestionTakenId = 0,
            lastQuestionTakenNo = 0,
            qStartTakenDate = 0,
            dataStatus = 0
        ),

        TblCategory(
            pkCategoryId = generatePrimaryKey(),
            title = "Sports",
            description = "Questions about different sports, athletes, and tournaments",
            note = null,
            iconFilePath = null,
            noteFilePath = null,
            totalQuestions = 50,
            totalCorrectAnswers = 0,
            totalWrongAnswers = 0,
            totalQAnswered = 0,
            lastQuestionTakenId = 0,
            lastQuestionTakenNo = 0,
            qStartTakenDate = 0,
            dataStatus = 0
        ),

        TblCategory(
            pkCategoryId = generatePrimaryKey(),
            title = "Movies",
            description = "Cinema, actors, directors, and famous films",
            note = null,
            iconFilePath = null,
            noteFilePath = null,
            totalQuestions = 50,
            totalCorrectAnswers = 0,
            totalWrongAnswers = 0,
            totalQAnswered = 0,
            lastQuestionTakenId = 0,
            lastQuestionTakenNo = 0,
            qStartTakenDate = 0,
            dataStatus = 0
        ),

        TblCategory(
            pkCategoryId = generatePrimaryKey(),
            title = "Music",
            description = "Music artists, songs, genres, and music history",
            note = null,
            iconFilePath = null,
            noteFilePath = null,
            totalQuestions = 50,
            totalCorrectAnswers = 0,
            totalWrongAnswers = 0,
            totalQAnswered = 0,
            lastQuestionTakenId = 0,
            lastQuestionTakenNo = 0,
            qStartTakenDate = 0,
            dataStatus = 0
        ),

        TblCategory(
            pkCategoryId = generatePrimaryKey(),
            title = "Literature",
            description = "Books, authors, poems, and literary works",
            note = null,
            iconFilePath = null,
            noteFilePath = null,
            totalQuestions = 50,
            totalCorrectAnswers = 0,
            totalWrongAnswers = 0,
            totalQAnswered = 0,
            lastQuestionTakenId = 0,
            lastQuestionTakenNo = 0,
            qStartTakenDate = 0,
            dataStatus = 0
        ),

        TblCategory(
            pkCategoryId = generatePrimaryKey(),
            title = "Health",
            description = "Human body, medicine, and healthy living",
            note = null,
            iconFilePath = null,
            noteFilePath = null,
            totalQuestions = 50,
            totalCorrectAnswers = 0,
            totalWrongAnswers = 0,
            totalQAnswered = 0,
            lastQuestionTakenId = 0,
            lastQuestionTakenNo = 0,
            qStartTakenDate = 0,
            dataStatus = 0
        ),

        TblCategory(
            pkCategoryId = generatePrimaryKey(),
            title = "Space",
            description = "Planets, astronomy, space missions, and the universe",
            note = null,
            iconFilePath = null,
            noteFilePath = null,
            totalQuestions = 50,
            totalCorrectAnswers = 0,
            totalWrongAnswers = 0,
            totalQAnswered = 0,
            lastQuestionTakenId = 0,
            lastQuestionTakenNo = 0,
            qStartTakenDate = 0,
            dataStatus = 0
        ),

        TblCategory(
            pkCategoryId = generatePrimaryKey(),
            title = "Animals",
            description = "Animal species, habitats, and wildlife knowledge",
            note = null,
            iconFilePath = null,
            noteFilePath = null,
            totalQuestions = 50,
            totalCorrectAnswers = 0,
            totalWrongAnswers = 0,
            totalQAnswered = 0,
            lastQuestionTakenId = 0,
            lastQuestionTakenNo = 0,
            qStartTakenDate = 0,
            dataStatus = 0
        ),

        TblCategory(
            pkCategoryId = generatePrimaryKey(),
            title = "Food and Cooking",
            description = "Cuisines, ingredients, cooking techniques, and food culture",
            note = null,
            iconFilePath = null,
            noteFilePath = null,
            totalQuestions = 50,
            totalCorrectAnswers = 0,
            totalWrongAnswers = 0,
            totalQAnswered = 0,
            lastQuestionTakenId = 0,
            lastQuestionTakenNo = 0,
            qStartTakenDate = 0,
            dataStatus = 0
        ),

        TblCategory(
            pkCategoryId = generatePrimaryKey(),
            title = "Art and Culture",
            description = "Painting, sculpture, traditions, and cultural knowledge",
            note = null,
            iconFilePath = null,
            noteFilePath = null,
            totalQuestions = 50,
            totalCorrectAnswers = 0,
            totalWrongAnswers = 0,
            totalQAnswered = 0,
            lastQuestionTakenId = 0,
            lastQuestionTakenNo = 0,
            qStartTakenDate = 0,
            dataStatus = 0
        ),

        TblCategory(
            pkCategoryId = generatePrimaryKey(),
            title = "Language",
            description = "Vocabulary, grammar, and language related questions",
            note = null,
            iconFilePath = null,
            noteFilePath = null,
            totalQuestions = 50,
            totalCorrectAnswers = 0,
            totalWrongAnswers = 0,
            totalQAnswered = 0,
            lastQuestionTakenId = 0,
            lastQuestionTakenNo = 0,
            qStartTakenDate = 0,
            dataStatus = 0
        ),

        TblCategory(
            pkCategoryId = generatePrimaryKey(),
            title = "Logic and Puzzles",
            description = "Brain teasers, reasoning questions, and logical puzzles",
            note = null,
            iconFilePath = null,
            noteFilePath = null,
            totalQuestions = 50,
            totalCorrectAnswers = 0,
            totalWrongAnswers = 0,
            totalQAnswered = 0,
            lastQuestionTakenId = 0,
            lastQuestionTakenNo = 0,
            qStartTakenDate = 0,
            dataStatus = 0
        ),

        TblCategory(
            pkCategoryId = generatePrimaryKey(),
            title = "Bank",
            description = "Questions about banking systems, financial institutions, and banking services",
            note = null,
            iconFilePath = null,
            noteFilePath = null,
            totalQuestions = 50,
            totalCorrectAnswers = 0,
            totalWrongAnswers = 0,
            totalQAnswered = 0,
            lastQuestionTakenId = 0,
            lastQuestionTakenNo = 0,
            qStartTakenDate = 0,
            dataStatus = 0
        ),

        TblCategory(
            pkCategoryId = generatePrimaryKey(),
            title = "Papua New Guinea",
            description = "Questions about the history, culture, geography, and people of Papua New Guinea",
            note = null,
            iconFilePath = null,
            noteFilePath = null,
            totalQuestions = 50,
            totalCorrectAnswers = 0,
            totalWrongAnswers = 0,
            totalQAnswered = 0,
            lastQuestionTakenId = 0,
            lastQuestionTakenNo = 0,
            qStartTakenDate = 0,
            dataStatus = 0
        ),

        TblCategory(
            pkCategoryId = generatePrimaryKey(),
            title = "Port Moresby (PNG)",
            description = "Questions about Port Moresby including landmarks, history, and local facts",
            note = null,
            iconFilePath = null,
            noteFilePath = null,
            totalQuestions = 50,
            totalCorrectAnswers = 0,
            totalWrongAnswers = 0,
            totalQAnswered = 0,
            lastQuestionTakenId = 0,
            lastQuestionTakenNo = 0,
            qStartTakenDate = 0,
            dataStatus = 0
        )
    )

    CoroutineScope(Dispatchers.IO).launch {
        categories.forEach {
            controller.insertCategory(it)
        }
        print("INSERT: DONE!")
    }
}

