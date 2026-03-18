package com.fera.kuiz.feat_main.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fera.kuiz.BuildConfig
import com.fera.kuiz.R
import com.fera.kuiz.common.util.Const
import com.fera.kuiz.databinding.ActivityMainBinding
import com.fera.kuiz.feat_CategoryQuestions.view.CategoryActivity
import com.fera.kuiz.feat_main.controller.ControllerMainAct
import com.fera.kuiz.feat_CategoryQuestions.model.TblCategory
import com.fera.kuiz.feat_takeQuiz.view.TakeQuizActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), InterfaceMainAct, AdapterMainActRecentCat.InterfaceAdapterRecentCat, AdapterMainActCat.InterfaceCategoryMain {
    private val TAG = "MainActivity"

    //########## CONST & DEFAULT VAL #############//
    private lateinit var b: ActivityMainBinding
    private lateinit var rvRecentQuestion: RecyclerView

    //########## VIEWS #############//
    private lateinit var adapterMainActRecentCat: AdapterMainActRecentCat
    private var listRecentCat = emptyList<TblCategory>()

    private lateinit var adapterMainActCat: AdapterMainActCat
    private var listCategory = emptyList<TblCategory>()

    private lateinit var controllerMain: ControllerMainAct

    override fun onCreate(savedInstanceState: Bundle?) {

//        if (intent.getStringExtra(Const.ACTIVITY_KEY) != BuildConfig.ACTIVITY_PASSWORD) {
//            finish()
//        }

        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(b.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        setStatusBarColor()
        setNavigationBarColor()


        initViews()
        addActionListeners()
    }

    private fun initViews() {
        controllerMain = ViewModelProvider(this)[ControllerMainAct::class.java]

        adapterMainActRecentCat = AdapterMainActRecentCat(listRecentCat, this, this)
        b.rvRecentCat.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        b.rvRecentCat.adapter = adapterMainActRecentCat

        controllerMain.categoriesRecent.observe(this@MainActivity) {
            adapterMainActRecentCat.updateList(it)
            listRecentCat = it
        }

        adapterMainActCat = AdapterMainActCat(listCategory, this, this)
        b.rvCategory.layoutManager = LinearLayoutManager(this)
        b.rvCategory.adapter = adapterMainActCat

        controllerMain.categories.observe(this@MainActivity) { it ->
            adapterMainActCat.updateList(it)
            listCategory = it
        }

    }

    private fun addActionListeners() {
        b.ivToggleNavViewMain.setOnClickListener {
            showHideSideDrawer()
        }

//        b.ivAddCategoryMain.setOnClickListener {
//            toastLong("Unable to add Categories now")
//
//            val intent = Intent(this, CategoryActivity::class.java)
//            intent.putExtra(Const.ACTIVITY_KEY, BuildConfig.ACTIVITY_PASSWORD)
//            startActivity(intent)
//        }
    }


    private fun showHideSideDrawer() {
        if (b.mainDrawer.isDrawerOpen(GravityCompat.START))
            b.mainDrawer.closeDrawer(GravityCompat.START)
        else
            b.mainDrawer.openDrawer(GravityCompat.START)
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

    override fun gotoCategoryActivity(tblCategory: TblCategory) {
        CoroutineScope(Dispatchers.IO).launch {
            val listQuestions = controllerMain.getQuestionList(tblCategory.pkCategoryId)

            withContext(Dispatchers.Main){
                val intent = Intent(this@MainActivity, CategoryActivity::class.java)
                intent.putExtra(Const.ACTIVITY_KEY, BuildConfig.ACTIVITY_PASSWORD)
                intent.putExtra(Const.CATEGORY, tblCategory)
                intent.putExtra(Const.QUESTION_LIST, ArrayList(listQuestions))
                startActivity(intent)
            }
        }
    }

    override fun gotoTakeQuizActivity(pkCategoryId: Long, continueQuestion: Boolean, questionNo: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val holderCatQuestAndAns = controllerMain.getHolderCatQuestAndAns(pkCategoryId)

            Log.d(TAG, "gotoTakeQuizActivity: $holderCatQuestAndAns")

            withContext(Dispatchers.Main){
                val intent = Intent(this@MainActivity, TakeQuizActivity::class.java)
                intent.putExtra(Const.ACTIVITY_KEY, BuildConfig.ACTIVITY_PASSWORD)
                intent.putExtra(Const.CONTINUE_QUESTION, continueQuestion)
                intent.putExtra(Const.CONTINUE_QUESTION_NO, questionNo)
                intent.putExtra(Const.HolderCatQuestAndAns, holderCatQuestAndAns)
                startActivity(intent)
            }
        }
    }

    fun gotoTakeQuizActivityFirst(pkCategoryId: Long, continueQuestion: Boolean, questionNo: Int) {
        CoroutineScope(Dispatchers.IO).launch {

            val holderCatQuestAndAns = controllerMain.getHolderCatQuestAndAnsFirst(pkCategoryId, questionNo)

            Log.d(TAG, "gotoTakeQuizActivity: $holderCatQuestAndAns")

            withContext(Dispatchers.Main){
                val intent = Intent(this@MainActivity, TakeQuizActivity::class.java)
                intent.putExtra(Const.ACTIVITY_KEY, BuildConfig.ACTIVITY_PASSWORD)
                intent.putExtra(Const.CONTINUE_QUESTION, continueQuestion)
                intent.putExtra(Const.CONTINUE_QUESTION_NO, questionNo)
                intent.putExtra(Const.HolderCatQuestAndAns, holderCatQuestAndAns)
                startActivity(intent)
            }
        }
    }
}