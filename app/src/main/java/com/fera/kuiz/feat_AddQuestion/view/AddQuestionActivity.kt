package com.fera.kuiz.feat_AddQuestion.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import com.fera.kuiz.common.util.EnumQuestionType
import com.fera.kuiz.common.util.generatePrimaryKey
import com.fera.kuiz.common.util.hideKeyboard
import com.fera.kuiz.common.util.showKeyboard
import com.fera.kuiz.common.util.toastLong
import com.fera.kuiz.common.util.toastShort
import com.fera.kuiz.databinding.ActivityAddQuestionBinding
import com.fera.kuiz.feat_AddQuestion.controller.ControllerAddQuestAct
import com.fera.kuiz.feat_AddQuestion.model.answer.TblAnswer
import com.fera.kuiz.feat_AddQuestion.model.question.TblQuestion
import com.fera.kuiz.feat_CategoryQuestions.model.TblCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddQuestionActivity : AppCompatActivity(), AdapterAddQuestAct.InterfaceAdapterAddQuestAct {
    private val TAG = "AddQuestionActivity"

    private lateinit var b: ActivityAddQuestionBinding

    private var tblCategory: TblCategory? = null
    private var tblQuestion = TblQuestion(pkQuestionId = generatePrimaryKey())
    private var listAnswerMC = arrayListOf<TblAnswer>()
    private var listAnswerTF = arrayListOf<TblAnswer>()

    private lateinit var adapterAddQuestAct: AdapterAddQuestAct

    private lateinit var controllerAddQuestAct: ControllerAddQuestAct

    override fun onCreate(savedInstanceState: Bundle?) {
        if (intent.getStringExtra(Const.ACTIVITY_KEY) != BuildConfig.ACTIVITY_PASSWORD) {
            finish()
        }

        loadParcelData()

        super.onCreate(savedInstanceState)
        b = ActivityAddQuestionBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(b.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setMainData()

        initViews()
        addActionListeners()
    }


    private fun addActionListeners() {
        b.ivBackAddQues.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        b.ivSaveEditAddQues.setOnClickListener {
            hideKeyboard(this, b.edtQuestionTextAddQues)

            if (b.ivSaveEditAddQues.tag == ContextCompat.getString(this, R.string.txtSave)){
                saveQuestion()
            } else {
                b.ivSaveEditAddQues.setImageResource(R.drawable.ic_check)
                b.ivSaveEditAddQues.tag = ContextCompat.getString(this, R.string.txtSave)
            }
        }
        b.spQuestionTypeAddQues.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

                when (b.spQuestionTypeAddQues.selectedItem){
                    EnumQuestionType.MULTIPLE_CHOICE.type -> {
                        b.clMultipleChoiceAddQues.visibility = View.VISIBLE
                        b.clTrueOrFalseAddQues.visibility = View.GONE
                        tblQuestion.questionType = EnumQuestionType.MULTIPLE_CHOICE.type
                    }
                    EnumQuestionType.TRUE_OR_FALSE.type -> {
                        b.clMultipleChoiceAddQues.visibility = View.GONE
                        b.clTrueOrFalseAddQues.visibility = View.VISIBLE
                        tblQuestion.questionType = EnumQuestionType.TRUE_OR_FALSE.type
                    }
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        b.ivAddChoiceAddQues.setOnClickListener {
            b.bgDimAddQuestion.visibility = View.VISIBLE
            b.clAddChoiceAddQues.visibility = View.VISIBLE
            showKeyboard(this, b.edtChoiceAddQues)
        }
        b.ivCloseAddChoiceAddQues.setOnClickListener {
            b.edtChoiceAddQues.setText("")
            b.bgDimAddQuestion.visibility = View.GONE
            b.clAddChoiceAddQues.visibility = View.GONE
            hideKeyboard(this, b.edtChoiceAddQues)
        }
        b.btnCancelChoiceAddQues.setOnClickListener {
            b.edtChoiceAddQues.setText("")
            b.bgDimAddQuestion.visibility = View.GONE
            b.clAddChoiceAddQues.visibility = View.GONE
            hideKeyboard(this, b.edtChoiceAddQues)
        }
        b.btnAddChoiceAddQues.setOnClickListener {
            hideKeyboard(this, b.edtQuestionTextAddQues)
            b.bgDimAddQuestion.visibility = View.GONE
            b.clAddChoiceAddQues.visibility = View.GONE
            addChoiceToList()
        }
        b.bgDimAddQuestion.setOnClickListener {
            hideKeyboard(this, b.edtQuestionTextAddQues)
            b.bgDimAddQuestion.visibility = View.GONE
            b.clAddChoiceAddQues.visibility = View.GONE
        }
    }

    private fun getAnswers(): List<TblAnswer> {
        return when(tblQuestion.questionType){
            EnumQuestionType.MULTIPLE_CHOICE.type -> {
                if (listAnswerMC.size < 2){
                    toastLong("Add More Choices!")
                    emptyList()
                } else {
                    listAnswerMC
                }
            }
            EnumQuestionType.TRUE_OR_FALSE.type -> {
                val tblAnswerT = TblAnswer(generatePrimaryKey(), tblQuestion.pkQuestionId)
                val tblAnswerF = TblAnswer(generatePrimaryKey(), tblQuestion.pkQuestionId)

                val descTrue = b.edtTrueDescAddQues.text.toString().trim()
                val descFalse = b.edtFalseDescAddQues.text.toString().trim()
                tblAnswerT.description = descTrue
                tblAnswerF.description = descFalse

                tblAnswerT.answer = "True"
                tblAnswerF.answer = "False"

                if (b.rgTrueFalseAddQues.checkedRadioButtonId == R.id.rbTrueAddQues){
                    tblAnswerT.isCorrect = true
                } else {
                    tblAnswerF.isCorrect = true
                }

                val list = arrayListOf<TblAnswer>(tblAnswerT, tblAnswerF)
                listAnswerTF = list
                listAnswerTF
            }
            else -> { emptyList() }
        }
    }

    private fun saveQuestion() {
        if (tblCategory == null){
            toastLong("No Category Selected!")
            return
        }

        if (b.edtQuestionTextAddQues.text.toString().trim().isEmpty()) {
            toastShort("No Question!")
            return
        }


        val listAnswers = getAnswers()

        tblQuestion.fkQuestion_categoryId = tblCategory!!.pkCategoryId
        tblQuestion.questionNo = tblCategory!!.totalQuestions + 1
        tblQuestion.question = b.edtQuestionTextAddQues.text.toString().trim()

        tblCategory!!.totalQuestions += 1


        if (listAnswers.isEmpty()){
            toastLong("No Questions!")
            return
        }

        lifecycleScope.launch {
            controllerAddQuestAct.insertQuestion(tblQuestion)
            listAnswers.forEach {
                controllerAddQuestAct.insertAnswer(it)
            }
            controllerAddQuestAct.updateCategory(tblCategory!!)

            withContext(Dispatchers.Main){
                b.ivSaveEditAddQues.setImageResource(R.drawable.ic_edit)
                b.ivSaveEditAddQues.tag = ContextCompat.getString(this@AddQuestionActivity, R.string.txtEdit)
            }
        }
    }

    private fun addChoiceToList() {
        if (tblCategory == null){
            toastLong("No Category Selected!")
            return
        }
        if (listAnswerMC.size >= 4){
            toastLong("Questions can't be more than 4")
            return
        }

        val answer = b.edtChoiceAddQues.text.toString().trim()

        val isCorrect = when(b.rgCorrectIncorrectAddChoiceAddQues.checkedRadioButtonId){
            b.rbCorrectAddChoiceAddQues.id -> { true }
            else -> { false }
        }

        val description = b.edtChoiceDescAddQues.text.toString().trim()
        val tblAnswer = TblAnswer(generatePrimaryKey(), tblQuestion.pkQuestionId, answer, description, isCorrect)


        b.edtChoiceAddQues.setText("")
        b.edtChoiceDescAddQues.setText("")
        b.rbCorrectAddChoiceAddQues.isChecked = false
        b.rbIncorrectAddChoiceAddQues.isChecked = true


        listAnswerMC.add(0, tblAnswer)
        adapterAddQuestAct.updateList(listAnswerMC)
    }

    private fun initViews() {
        controllerAddQuestAct = ViewModelProvider(this)[ControllerAddQuestAct::class.java]

        val listQuestionTypes = listOf<String>(EnumQuestionType.MULTIPLE_CHOICE.type, EnumQuestionType.TRUE_OR_FALSE.type)
        b.spQuestionTypeAddQues.adapter = ArrayAdapter<String>(this, R.layout.spinner_items, listQuestionTypes)

        adapterAddQuestAct = AdapterAddQuestAct(listAnswerMC, this, this)
        b.rvAddOptionsAddQues.layoutManager = LinearLayoutManager(this)
        b.rvAddOptionsAddQues.adapter = adapterAddQuestAct


        // GET & UPDATE the current TblCategory
        tblCategory?.pkCategoryId?.let { pkCatId ->
            lifecycleScope.launch {
                val totalQuest = controllerAddQuestAct.getTotalQuestionsInCategory(pkCatId)
                tblCategory?.totalQuestions = totalQuest

                tblCategory?.let { controllerAddQuestAct.updateCategory(it) }
            }
        }
    }

    private fun setMainData() {
        b.apply {
            tvCategoryTitleAddQues.text = tblCategory?.title
            tvQuestionsCountAddQues.text = String.format("${tblCategory?.totalQuestions} questions")
        }
    }

    private fun loadParcelData() {
        val category = intent.getParcelableExtra<TblCategory>(Const.CATEGORY)
        if (category == null) {
            toastLong("Error Loading Data")
            return
        }
        tblCategory = category
    }

    override fun deleteAnswer(pkAnswerId: Long) {
        lifecycleScope.launch {
            controllerAddQuestAct.deleteAnswer(pkAnswerId)
        }
    }
}