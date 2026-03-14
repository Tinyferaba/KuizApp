package com.fera.kuiz.feat_CategoryQuestions.view

import android.os.Build
import android.os.Bundle
import android.view.View
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
import com.fera.kuiz.databinding.ActivityCategoryBinding
import com.fera.kuiz.feat_CategoryQuestions.controller.CategoryController
import com.fera.kuiz.feat_CategoryQuestions.model.question.TblQuestion
import com.fera.kuiz.feat_takeQuiz.model.TblCategory
import kotlinx.coroutines.launch

class CategoryActivity : AppCompatActivity() {
    private lateinit var b: ActivityCategoryBinding

    private var tblCategory: TblCategory ?= null

    private lateinit var adapterQuestion: AdapterQuestion

    private lateinit var categoryController: CategoryController
    private var listQuestion = emptyList<TblQuestion>()

    override fun onCreate(savedInstanceState: Bundle?) {

        if (intent.getStringExtra(Const.ACTIVITY_KEY) != BuildConfig.ACTIVITY_PASSWORD) {
            finish()
        }

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
        b.ivbackCat.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun initView() {
        categoryController = ViewModelProvider(this)[CategoryController::class.java]

        adapterQuestion = AdapterQuestion(listQuestion, this)
        b.rvQuestionsCat.layoutManager = LinearLayoutManager(this)
        b.rvQuestionsCat.adapter = adapterQuestion


    }

    private fun loadProperty(){
        tblCategory = intent.getParcelableExtra<TblCategory>(Const.CATEGORY)
        tblCategory?.let {
            b.pbCorrectCat.max = it.totalQAnswered
            b.pbCorrectCat.progress = it.totalCorrectAnswers

            b.pbProgressCat.max = it.totalQuestions
            b.pbProgressCat.progress = it.totalQAnswered

            lifecycleScope.launch {
                categoryController.getQuestions(it.pkCategoryId).let { list ->
                    listQuestion = list
                    adapterQuestion.updateList(listQuestion)
                }
            }
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
            decorView.systemUiVisibility = decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }

    private fun setNavigationBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            window.navigationBarColor = getColor(R.color.transparent)
    }

}