package com.fera.kuiz.feat_CategoryQuestions.view

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fera.kuiz.BuildConfig
import com.fera.kuiz.R
import com.fera.kuiz.common.util.Const

class AddQuestionActivity : AppCompatActivity() {
    private val TAG = "AddQuestionActivity"

    override fun onCreate(savedInstanceState: Bundle?) {

        if (intent.getStringExtra(Const.ACTIVITY_KEY) != BuildConfig.ACTIVITY_PASSWORD) {
            finish()
        }

        Log.d(TAG, "onCreate: WHorking: ${intent.getStringExtra(Const.ACTIVITY_KEY)}")

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_question)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}