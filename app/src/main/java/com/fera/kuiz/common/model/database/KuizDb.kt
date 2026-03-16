package com.fera.kuiz.common.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fera.kuiz.BuildConfig
import com.fera.kuiz.common.util.Converters
import com.fera.kuiz.feat_CategoryQuestions.model.answer.DaoAnswer
import com.fera.kuiz.feat_CategoryQuestions.model.answer.TblAnswer
import com.fera.kuiz.feat_CategoryQuestions.model.question.DaoQuestion
import com.fera.kuiz.feat_CategoryQuestions.model.question.TblQuestion
import com.fera.kuiz.feat_CategoryQuestions.model.userAnswer.DaoUserAnswer
import com.fera.kuiz.feat_CategoryQuestions.model.userAnswer.TblUserAnswer
import com.fera.kuiz.feat_takeQuiz.model.DaoCategory
import com.fera.kuiz.feat_takeQuiz.model.TblCategory
import com.fera.kuiz.feat_userAuth.model.TblUserRegistration
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(
    entities = [
        TblUserRegistration::class,
        TblCategory::class, TblUserAnswer::class, TblQuestion::class, TblAnswer::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [Converters::class])
abstract class KuizDb: RoomDatabase() {

    abstract fun daoCategory(): DaoCategory
    abstract fun daoQuestion(): DaoQuestion
    abstract fun daoAnswer(): DaoAnswer
    abstract fun daoUserAnswer(): DaoUserAnswer


    companion object {
        @Volatile
        private var INSTANCE: KuizDb?=null

        fun getDatabase(context: Context): KuizDb {
            val tempINSTANCE = INSTANCE
            if (tempINSTANCE != null)
                return tempINSTANCE

            val password = BuildConfig.PASSWORD_SQLITE_DB
            val bytePassword: ByteArray = SQLiteDatabase.getBytes(password.toCharArray())
            val factory = SupportFactory(bytePassword)

            synchronized(this){
                val temp = Room.databaseBuilder(
                    context.applicationContext,
                    KuizDb::class.java,
                    "kuiz_db.db")
//                    .openHelperFactory(factory)
                    .createFromAsset("database/kuiz_db.db")
                    .build()

//                deletePreloadedDatabase(context)

                INSTANCE = temp
                return temp
            }
        }

//        private fun deletePreloadedDatabase(context: Context){
//            // Delete preloaded database to reduce app size
//            CoroutineScope(Dispatchers.IO).launch {
//                delay(5000)
//                withContext(Dispatchers.Main){
//                    if (isFirstRun(context)){
//                        try {
//                            if (context.deleteDatabase("kuiz_db.db")) {
//                                Toast.makeText(context, "App Optimized Successfully!", Toast.LENGTH_SHORT).show()
//                            } else {
//                                Toast.makeText(context, "Failed to Optimize.", Toast.LENGTH_SHORT).show()
//                            }
//                        } catch (e: Exception) {
//                            Toast.makeText(context, "Error deleting preloaded database: ${e.message}", Toast.LENGTH_SHORT).show()
//                        }
//                        markAsRan(context)
//                    }
//                }
//            }
//        }

//        private fun markAsRan(context: Context) {
//            val editor = context.getSharedPreferences(Const.SHARED_PREF_DB, AppCompatActivity.MODE_PRIVATE).edit()
//            editor.putBoolean(Const.PreloadedDBDeleted, false)
//            editor.apply()
//        }
//
//        private fun isFirstRun(context: Context): Boolean {
//            val shared = context.getSharedPreferences(Const.SHARED_PREF_DB, AppCompatActivity.MODE_PRIVATE)
//            val isRun = shared.getBoolean(Const.PreloadedDBDeleted, true)
//            return isRun
//        }
    }
}