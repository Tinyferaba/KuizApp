package com.fera.kuiz.feat_takeQuiz.view

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fera.kuiz.BuildConfig
import com.fera.kuiz.R
import com.fera.kuiz.common.util.Const
import com.fera.kuiz.databinding.ActivityTakeQuizBinding
import com.fera.kuiz.feat_CategoryQuestions.model.question.HolderQuesAnsAndUserAns
import com.fera.kuiz.feat_takeQuiz.model.TblCategory

class TakeQuizActivity : AppCompatActivity() {
    private val TAG = "TakeQuizActivity"

    private lateinit var b: ActivityTakeQuizBinding

    private lateinit var listHolderQAndA: HolderQuesAnsAndUserAns

    override fun onCreate(savedInstanceState: Bundle?) {

        if (intent.getStringExtra(Const.ACTIVITY_KEY) != BuildConfig.ACTIVITY_PASSWORD) {
            finish()
        }

        super.onCreate(savedInstanceState)
        b = ActivityTakeQuizBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(b.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        addActionListeners()

    }

    private fun addActionListeners() {
        b.ivBackTakeQues.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


    }

    private fun initViews() {

    }
}