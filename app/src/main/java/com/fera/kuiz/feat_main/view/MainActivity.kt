package com.fera.kuiz.feat_main.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fera.kuiz.BuildConfig
import com.fera.kuiz.R
import com.fera.kuiz.common.util.Const
import com.fera.kuiz.common.util.toastLong
import com.fera.kuiz.databinding.ActivityMainBinding
import com.fera.kuiz.feat_Questions.model.question.HolderQuesAnsAndUserAns
import com.fera.kuiz.feat_Questions.view.AddQuestionActivity
import com.fera.kuiz.feat_Questions.view.CategoryActivity
import com.fera.kuiz.feat_main.controller.MainController
import com.fera.kuiz.feat_takeQuiz.model.TblCategory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), AdapterCategoryActions {

    //########## CONST & DEFAULT VAL #############//
    private lateinit var b: ActivityMainBinding
    private lateinit var rvRecentQuestion: RecyclerView

    //########## VIEWS #############//
    private lateinit var adapterRecentCat: AdapterRecentCat
    private var listRecentCat = emptyList<TblCategory>()

    private lateinit var adapterCategory: AdapterCategory
    private var listCategory = emptyList<TblCategory>()

    private lateinit var mainController: MainController

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

    private fun initViews(){
        mainController = ViewModelProvider(this)[MainController::class.java]

        adapterRecentCat = AdapterRecentCat(listRecentCat, this, this)
        b.rvRecentCat.layoutManager = LinearLayoutManager(this)
        b.rvRecentCat.adapter = adapterRecentCat

        lifecycleScope.launch {
            mainController.getRecentCategories().let {
                listRecentCat = it
                adapterRecentCat.updateList(listRecentCat)
            }
        }

        adapterCategory = AdapterCategory(listCategory, this, this)
        b.rvCategory.layoutManager = LinearLayoutManager(this)
        b.rvCategory.adapter = adapterCategory

        lifecycleScope.launch {
            mainController.getCategories().let {
                listCategory = it
                adapterCategory.updateList(listCategory)
            }
        }
    }

    private fun addActionListeners(){
        b.ivToggleNavViewMain.setOnClickListener {
            showHideSideDrawer()
        }

        b.ivToggleNavViewMain.setOnClickListener {
            val intent = Intent(this, AddQuestionActivity::class.java)
            intent.putExtra(Const.ACTIVITY_KEY, BuildConfig.ACTIVITY_PASSWORD)
            startActivity(intent)
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
            decorView.systemUiVisibility = decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }

    private fun setNavigationBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            window.navigationBarColor = getColor(R.color.transparent)
    }

    override suspend fun getQuestion(pkLastQuestionTakenId: Long): HolderQuesAnsAndUserAns {
        return mainController.getQuestionAnswerAndUserAnswer(pkLastQuestionTakenId)
    }

}