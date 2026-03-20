package com.fera.kuiz.feat_takeQuiz.view

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.fera.kuiz.BuildConfig
import com.fera.kuiz.R
import com.fera.kuiz.common.util.Const
import com.fera.kuiz.common.util.toastLong
import com.fera.kuiz.common.util.toastShort
import com.fera.kuiz.databinding.ActivityTakeQuizBinding
import com.fera.kuiz.feat_AddQuestion.model.question.HolderCatQuestAndAns
import com.fera.kuiz.feat_AddQuestion.model.question.HolderCatQuestAndAnsFirst
import com.fera.kuiz.feat_takeQuiz.model.userAnswer.TblUserAnswer
import com.fera.kuiz.feat_takeQuiz.controller.ControllerTakeQuizAct
import com.fera.kuiz.feat_CategoryQuestions.model.TblCategory
import kotlinx.coroutines.launch
import java.util.Date

class TakeQuizActivity : AppCompatActivity(), InterfaceTakeQuizAct, AdapterAnswerTakeQuizAct.InterfaceAdapterAnswer {
    // TODO: Fix Progress Tracking

    // ###########  CONST & VALUES  #############
    private val TAG = "TakeQuizActivity"

    private var continueQuestion = false
    private var currentQuestionPosition = 0
    private var totalQuestions = 0

    // *********** CATEGORY PROPERTIES *************
    private var tblUserAnswerSelected: TblUserAnswer? = null
    private var totalCorrectAnswers = 0
    private var totalWrongAnswers = 0
    private var totalQAnswered = 0
    private var lastQTakenQuestionId = 0L
    private var lastQTakenQuestionNo = 0


    // ###########  VIEWS  #############
    private lateinit var b: ActivityTakeQuizBinding


    private lateinit var adapterAnswer: AdapterAnswerTakeQuizAct
    private lateinit var holderCatQuesAndAns: HolderCatQuestAndAns
    private lateinit var holderCatQuestAndAnsFirst: HolderCatQuestAndAnsFirst

    private lateinit var controllerTakeQuiz: ControllerTakeQuizAct

    override fun onCreate(savedInstanceState: Bundle?) {

        if (intent.getStringExtra(Const.ACTIVITY_KEY) != BuildConfig.ACTIVITY_PASSWORD) {
            finish()
        }

        loadParcelData()

        super.onCreate(savedInstanceState)
        b = ActivityTakeQuizBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(b.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setMainData()


        setStatusBarColor()
        setNavigationBarColor()


        initViews()
        addActionListeners()

    }

    override fun onDestroy() {
        continueQuestion = false
        currentQuestionPosition = 0
        totalQuestions = 0
        totalCorrectAnswers = 0
        totalWrongAnswers = 0
        totalQAnswered = 0
        lastQTakenQuestionId = 0L
        lastQTakenQuestionNo = 0

        super.onDestroy()
    }

    private fun addActionListeners() {
        b.ivBackTakeQues.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        b.ivDeleteQuestTakeQues.setOnClickListener {
            lifecycleScope.launch {
                controllerTakeQuiz.deleteQuestion(holderCatQuesAndAns.listHolderQuestAndAns[currentQuestionPosition].tblQuestion.pkQuestionId)
            }
        }
//        b.sbDifficultyLevelTakeQues.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                val difficulty = progress + 1 // Converts 0-9 to 1-10. SeekBar takes 0 as 1
//
//                b.tvDifficultyDescTakeQues.text = "Difficulty: $difficulty"
//                when (difficulty) {
//                    in 0..3 -> {
//                        b.sbDifficultyLevelTakeQues.progressTintList = ColorStateList.valueOf(ContextCompat.getColor(this@TakeQuizActivity, R.color.green))
//                        b.sbDifficultyLevelTakeQues.thumbTintList = ColorStateList.valueOf(ContextCompat.getColor(this@TakeQuizActivity, R.color.green))
//                    }
//                    in 4..7 -> {
//                        b.sbDifficultyLevelTakeQues.progressTintList = ColorStateList.valueOf(ContextCompat.getColor(this@TakeQuizActivity, R.color.orange))
//                        b.sbDifficultyLevelTakeQues.thumbTintList = ColorStateList.valueOf(ContextCompat.getColor(this@TakeQuizActivity, R.color.orange))
//                    }
//                    in 8..10 -> {
//                        b.sbDifficultyLevelTakeQues.progressTintList = ColorStateList.valueOf(ContextCompat.getColor(this@TakeQuizActivity, R.color.red))
//                        b.sbDifficultyLevelTakeQues.thumbTintList = ColorStateList.valueOf(ContextCompat.getColor(this@TakeQuizActivity, R.color.red))
//                    }
//                }
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
//
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
//        })
        b.ivNextTakeQues.setOnClickListener {
            if (tblUserAnswerSelected != null) {
                tblUserAnswerSelected?.let {
                    saveUserAnswer(it)   //Note: Save User Answer before loading the next Question
                }
                updateCategoryProperties()
                tblUserAnswerSelected = null

                currentQuestionPosition++
                loadAndSetNextQuestion(questionPos = currentQuestionPosition)
            } else {
                toastLong("Choice Not Selected")
            }
        }

        b.ivRevealAnswerTakeQues.setOnClickListener {
            if (b.ivRevealAnswerTakeQues.tag == ContextCompat.getString(this, R.string.tagClosed)) {
                b.ivRevealAnswerTakeQues.setImageResource(R.drawable.ic_eye_open)
                b.ivRevealAnswerTakeQues.tag = ContextCompat.getString(this, R.string.tagOpened)
                adapterAnswer.toggleAnswer(true)
            } else {
                b.ivRevealAnswerTakeQues.setImageResource(R.drawable.ic_eye_close)
                b.ivRevealAnswerTakeQues.tag = ContextCompat.getString(this, R.string.tagClosed)
                adapterAnswer.toggleAnswer(false)
            }
        }

        b.ivPreviousTakeQues.setOnClickListener {
            currentQuestionPosition--
            loadAndSetNextQuestion(questionPos = currentQuestionPosition)
        }

    }

    private fun initViews() {
        controllerTakeQuiz = ViewModelProvider(this)[ControllerTakeQuizAct::class.java]

        lifecycleScope.launch {
            holderCatQuesAndAns = controllerTakeQuiz.getHolderCatQuestAndAns(holderCatQuestAndAnsFirst.tblCategory.pkCategoryId)
        }

        adapterAnswer = AdapterAnswerTakeQuizAct(emptyList(), this, this)
        b.rvChoiceTakeQues.layoutManager = LinearLayoutManager(this)
        b.rvChoiceTakeQues.adapter = adapterAnswer

        //Note: Called only once to load the first question
        loadAndSetNextQuestion(true, currentQuestionPosition-1)

        lifecycleScope.launch {
            controllerTakeQuiz.getTotalCorrectIncorrectAnswers(holderCatQuestAndAnsFirst.tblCategory.pkCategoryId, 1).let { totalCorrectAnswers = it }
            controllerTakeQuiz.getTotalCorrectIncorrectAnswers(holderCatQuestAndAnsFirst.tblCategory.pkCategoryId, 0).let { totalWrongAnswers = it }
            controllerTakeQuiz.getTotalQAnswered(holderCatQuestAndAnsFirst.tblCategory.pkCategoryId).let { totalQAnswered = it }
        }
    }


    private fun loadParcelData() {
//        val holderList = intent.getParcelableExtra<HolderCatQuestAndAns>(Const.HolderCatQuestAndAns)
        val holderList = intent.getParcelableExtra<HolderCatQuestAndAnsFirst>(Const.HolderCatQuestAndAnsFirst)
        val continueQues = intent.getBooleanExtra(Const.CONTINUE_QUESTION, false)
        val continueQuesNo = intent.getIntExtra(Const.CONTINUE_QUESTION_NO, 1)

        if (holderList != null) {

            currentQuestionPosition = continueQuesNo
//            holderCatQuesAndAns = holderList
            holderCatQuestAndAnsFirst = holderList
            continueQuestion = continueQues
//            totalQuestions = holderCatQuesAndAns.listHolderQuestAndAns.size
            totalQuestions = holderList.tblCategory.totalQuestions
        } else {
            toastLong("Error Loading Data")
            Log.d(TAG, "loadData: list: $holderList")
        }
    }

    private fun setMainData() {
//        b.tvCategoryTakeQues.text = holderCatQuesAndAns.tblCategory.title
        b.tvCategoryTakeQues.text = holderCatQuestAndAnsFirst.tblCategory.title
    }

    private fun loadAndSetNextQuestion(isFirst: Boolean = false, questionPos: Int) {
        if (isFirst){
            val cat = holderCatQuestAndAnsFirst.tblCategory
            val quest = holderCatQuestAndAnsFirst.tblQuestion
            b.tvQuestionNumberTakeQues.text = "${quest.questionNo} of ${cat.totalQuestions}"
            b.tvQuestionTakeQues.text = quest.question
//            b.sbDifficultyLevelTakeQues.progress = quest.difficulty - 1

            adapterAnswer.updateList(holderCatQuestAndAnsFirst.listAnswers)
            return
        }

        Log.d(TAG, "loadAndSetNextQuestion: Position: $questionPos")
        if (questionPos == holderCatQuesAndAns.listHolderQuestAndAns.size) {
            if (holderCatQuesAndAns.listHolderQuestAndAns.isEmpty()) {
                toastLong("No Questions")
            } else {
                toastShort("Hurray! Quiz Completed!")
            }
            currentQuestionPosition--
            onBackPressedDispatcher.onBackPressed()
        } else if (questionPos < 0) {
            currentQuestionPosition++
            toastShort("No Previous Questions")
        } else {
            val holderQuestAndAns = holderCatQuesAndAns.listHolderQuestAndAns[questionPos]
            b.tvQuestionNumberTakeQues.text = "${holderQuestAndAns.tblQuestion.questionNo} of ${holderCatQuesAndAns.listHolderQuestAndAns.size}"
            b.tvQuestionTakeQues.text = holderQuestAndAns.tblQuestion.question
//            b.sbDifficultyLevelTakeQues.progress = holderQuestAndAns.tblQuestion.difficulty - 1

            adapterAnswer.updateList(holderQuestAndAns.listAnswers)
        }
    }


    private fun setStatusBarColor() {
        /*  ######### Description #########
            Setting status bar color
            For SDK <21 make sure to check before setting it.
            e.g: if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){}
        */
//        window.statusBarColor = ContextCompat.getColor(this, R.color.themeColor)

        window.statusBarColor = ContextCompat.getColor(this, R.color.green_darkest)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decorView = window.decorView
            decorView.systemUiVisibility =
                decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }

    private fun setNavigationBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            window.navigationBarColor = getColor(R.color.transparent)
    }

    fun updateCategoryProperties() {
//        lastQTakenQuestionId = controllerTakeQuiz.getLastQTakenQuestionId(holderCatQuesAndAns.tblCategory.pkCategoryId).value ?: lastQTakenQuestionId
//        lastQTakenQuestionNo = controllerTakeQuiz.getLastQTakenQuestionNo(holderCatQuesAndAns.tblCategory.pkCategoryId).value ?: lastQTakenQuestionNo

        holderCatQuesAndAns.tblCategory.totalCorrectAnswers = totalCorrectAnswers
        holderCatQuesAndAns.tblCategory.totalWrongAnswers = totalWrongAnswers
        holderCatQuesAndAns.tblCategory.totalQAnswered = totalQAnswered
        holderCatQuesAndAns.tblCategory.lastQuestionTakenId = lastQTakenQuestionId
        holderCatQuesAndAns.tblCategory.lastQuestionTakenNo = lastQTakenQuestionNo
        holderCatQuesAndAns.tblCategory.qStartTakenDate = Date().time

        updateCategoryProperties(holderCatQuesAndAns.tblCategory)
    }


    override fun saveUserAnswer(tblUserAnswer: TblUserAnswer) {
        if (tblUserAnswer.isCorrect) {
            totalCorrectAnswers++
        } else {
            totalWrongAnswers++
        }

        totalQAnswered++

        lastQTakenQuestionId = tblUserAnswer.fkUserAnswer_questionId
        lastQTakenQuestionNo = holderCatQuesAndAns.listHolderQuestAndAns[currentQuestionPosition].tblQuestion.questionNo

        controllerTakeQuiz.saveUserAnswer(tblUserAnswer)

        holderCatQuesAndAns.listHolderQuestAndAns[currentQuestionPosition].tblQuestion.isTaken = true       // Question is taken so it must be updated
//        holderCatQuesAndAns.listHolderQuestAndAns[currentQuestionPosition].tblQuestion.difficulty = b.sbDifficultyLevelTakeQues.progress + 1
        controllerTakeQuiz.updateQuestion(holderCatQuesAndAns.listHolderQuestAndAns[currentQuestionPosition].tblQuestion)
    }

    override fun updateCategoryProperties(tblCategory: TblCategory) {
        controllerTakeQuiz.updateCategoryProperties(tblCategory)
    }

    override fun selectUserAnswer(tblUserAnswer: TblUserAnswer) {
        tblUserAnswerSelected = tblUserAnswer
    }

}
