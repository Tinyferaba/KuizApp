package com.fera.kuiz.feat_CategoryQuestions.view

import android.animation.ValueAnimator
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.fera.kuiz.BuildConfig
import com.fera.kuiz.R
import com.fera.kuiz.common.util.Const
import com.fera.kuiz.common.util.categoryIcons
import com.fera.kuiz.common.util.toastLong
import com.fera.kuiz.databinding.ActivityCategoryBinding
import com.fera.kuiz.feat_CategoryQuestions.controller.ControllerCategoryAct
import com.fera.kuiz.feat_AddQuestion.model.question.TblQuestion
import com.fera.kuiz.feat_AddQuestion.view.AddQuestionActivity
import com.fera.kuiz.feat_CategoryQuestions.model.TblCategory
import com.fera.kuiz.feat_takeQuiz.view.TakeQuizActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

class CategoryActivity : AppCompatActivity(), AdapterCategoryAct.InterfaceAdapterQuestion {
    private val TAG = "CategoryActivity"

    private lateinit var b: ActivityCategoryBinding
    private var tblCategory: TblCategory? = null

    private lateinit var adapterCategoryAct: AdapterCategoryAct
    private var listQuestion = emptyList<TblQuestion>()


    private lateinit var controllerCategory: ControllerCategoryAct

    override fun onCreate(savedInstanceState: Bundle?) {

        if (intent.getStringExtra(Const.ACTIVITY_KEY) != BuildConfig.ACTIVITY_PASSWORD) {
            finish()
        }

        loadParcelData()

        super.onCreate(savedInstanceState)
        b = ActivityCategoryBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(b.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setStatusBarColor()
        setNavigationBarColor()

        initView()
        addActionListeners()

    }


    private fun addActionListeners() {
        b.ivBackCat.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        b.ivEditCategoryIconCat.setOnClickListener {
            toastLong("Edit Icon Disabled")
        }
        b.ivTakeQuizCat.setOnClickListener {
            tblCategory?.pkCategoryId?.let {
                gotoTakeQuizActivity(it, false, 0)
            }
        }
        b.ivResetQuestionsTakeQues.setOnClickListener {
            b.ivResetQuestionsTakeQues.animate()
                .setDuration(500)
                .rotation(-360F)
                .scaleX(1.3F)
                .scaleY(1.3F)
                .withStartAction {
                    b.ivResetQuestionsTakeQues.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green_light))
                }
                .withEndAction {
                    b.ivResetQuestionsTakeQues.animate()
                        .setDuration(500)
                        .scaleX(1F)
                        .scaleY(1F)
                        .withEndAction {
                            b.ivResetQuestionsTakeQues.rotation = 0F
                            b.ivResetQuestionsTakeQues.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green_darkest))
                        }.start()
                }.start()

            tblCategory?.let {
                lifecycleScope.launch {
                    controllerCategory.deleteUserAnswersInCategory(it.pkCategoryId)
                    controllerCategory.resetQuestionsTakenStatus(it.pkCategoryId)

                    it.totalCorrectAnswers = 0
                    it.totalWrongAnswers = 0
                    it.totalQAnswered = 0
                    it.lastQuestionTakenId = 0L
                    it.lastQuestionTakenNo = 0
                    controllerCategory.updateCategory(it)
                }
            }
        }
        b.ivAddQuestionCat.setOnClickListener {
            val intent = Intent(this, AddQuestionActivity::class.java)
            intent.putExtra(Const.ACTIVITY_KEY, BuildConfig.ACTIVITY_PASSWORD)
            intent.putExtra(Const.CATEGORY, tblCategory)
            startActivity(intent)
        }
    }

    private fun initView() {
        controllerCategory = ViewModelProvider(this)[ControllerCategoryAct::class.java]

        adapterCategoryAct = AdapterCategoryAct(listQuestion, this, this)
        b.rvQuestionsCat.layoutManager = LinearLayoutManager(this)
        b.rvQuestionsCat.adapter = adapterCategoryAct



        val category = tblCategory ?: return
        val catId = category.pkCategoryId
        b.edtTitleCat.setText(category.title)
        b.tvCatDescriptionCat.text = category.description
        b.sivCategoryIconCat.setImageResource(categoryIcons[category.title] ?: R.drawable.ic_logo_circular)

        controllerCategory.getCategoryLive(catId).observe(this) { cat ->
            tblCategory = cat

//            cat.iconFilePath?.let { path ->
//                Glide.with(this)
//                    .load(path)
//                    .into(b.sivCategoryIconCat)
//            }
            controllerCategory.getQuestionsLive(catId).observe(this) { list ->
                adapterCategoryAct.updateList(list)
                listQuestion = list
            }
            loadStats(correctMax = cat.totalQAnswered, correctTotal = cat.totalCorrectAnswers, progressMax = cat.totalQuestions, progressTotal = cat.totalQAnswered)
        }
    }

    private fun loadStats(correctStart: Int=0, correctMax: Int, correctTotal: Int, progressStart: Int=0, progressMax: Int, progressTotal: Int, ) {
        try {
                b.pbCorrectCat.max = correctMax
                val animatorCorrect = ValueAnimator.ofInt(correctStart, correctTotal)
                animatorCorrect.duration = 1000
                animatorCorrect.addUpdateListener {
                    val value = it.getAnimatedValue() as Int
                    val percentage = 100 * (value / correctMax.toDouble())
                    b.pbCorrectCat.progress = value
                    b.tvCorrectPercent.text = "${percentage.toInt()} %"
                }
                animatorCorrect.start()

                b.pbProgressCat.max = progressMax
                val animatorProgress = ValueAnimator.ofInt(progressStart, progressTotal)
                animatorProgress.duration = 1000
                animatorProgress.addUpdateListener {
                    val value = it.getAnimatedValue() as Int
                    val percentage = 100 * (value / progressMax.toDouble())
                    b.pbProgressCat.progress = value
                    b.tvProgressPercent.text = "${percentage.roundToInt()} %"
                }
                animatorProgress.start()

        } catch (e: Exception) {
            Log.d(TAG, "loadStats: Error: $e")
        }
    }

    private fun loadParcelData() {
        val category = intent.getParcelableExtra<TblCategory>(Const.CATEGORY)
//        val questionsList = intent.getParcelableArrayListExtra<TblQuestion>(Const.QUESTION_LIST)

        if (category == null) {
            //   || questionsList == null
            toastLong("Error Loading Data")
//            Log.d(TAG, "loadAllData: \tCategory: $category\tQuestionList: $questionsList")
            return
        }

//        listQuestion = questionsList
        tblCategory = category
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
            decorView.systemUiVisibility = decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }

    private fun setNavigationBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            window.navigationBarColor = getColor(R.color.transparent)
    }

//    override fun gotoTakeQuizActivity(pkCategoryId: Long, continueQuestion: Boolean, questionNo: Int) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val holderCatQuestAndAns = controllerCategory.getHolderCatQuestAndAns(pkCategoryId)
//
//            Log.d(TAG, "gotoTakeQuizActivity: $holderCatQuestAndAns")
//
//            withContext(Dispatchers.Main){
//                val intent = Intent(this@CategoryActivity, TakeQuizActivity::class.java)
//                intent.putExtra(Const.ACTIVITY_KEY, BuildConfig.ACTIVITY_PASSWORD)
//                intent.putExtra(Const.CONTINUE_QUESTION, continueQuestion)
//                intent.putExtra(Const.CONTINUE_QUESTION_NO, questionNo)
//                intent.putExtra(Const.HolderCatQuestAndAns, holderCatQuestAndAns)
//                startActivity(intent)
//            }
//        }
//    }

    override fun gotoTakeQuizActivity(pkCategoryId: Long, continueQuestion: Boolean, questionNo: Int) {
        CoroutineScope(Dispatchers.IO).launch {

            val holderCatQuestAndAns = controllerCategory.getHolderCatQuestAndAnsFirst(pkCategoryId, questionNo)

            Log.d(TAG, "gotoTakeQuizActivity: $holderCatQuestAndAns")

            withContext(Dispatchers.Main) {
                val intent = Intent(this@CategoryActivity, TakeQuizActivity::class.java)
                intent.putExtra(Const.ACTIVITY_KEY, BuildConfig.ACTIVITY_PASSWORD)
                intent.putExtra(Const.CONTINUE_QUESTION, continueQuestion)
                intent.putExtra(Const.CONTINUE_QUESTION_NO, questionNo)
                intent.putExtra(Const.HolderCatQuestAndAnsFirst, holderCatQuestAndAns)
                startActivity(intent)
            }
        }
    }

    override fun editQuestion(tblQuestion: TblQuestion) {
        lifecycleScope.launch {
            val listAnswers = controllerCategory.getAnswers(tblQuestion.pkQuestionId)

            withContext(Dispatchers.Main) {
                val intent = Intent(this@CategoryActivity, AddQuestionActivity::class.java)
                intent.putExtra(Const.ACTIVITY_KEY, BuildConfig.ACTIVITY_PASSWORD)
                intent.putExtra(Const.QUESTION, tblQuestion)
                intent.putExtra(Const.CATEGORY, tblCategory)
                intent.putExtra(Const.EDIT, true)
                intent.putParcelableArrayListExtra(Const.ANSWER_LIST, ArrayList(listAnswers))
                startActivity(intent)
            }
        }


    }

}