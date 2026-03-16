package com.fera.kuiz.app

import android.app.Application
import com.fera.kuiz.common.util.ConstSharedPref
import androidx.core.content.edit

class App : Application() {

    override fun onCreate() {
        super.onCreate()




        if (isFirstRun(this)) {
            //######### RUN ONCE codes Here #########//


            markAsRan(this)
        }
        markAsRan(this)
    }



    private fun markAsRan(application: Application) {
        application.getSharedPreferences(ConstSharedPref.KUIZ_DB, MODE_PRIVATE).edit {
            putBoolean(ConstSharedPref.IS_FIRST_RUN, false)
        }
    }

    private fun isFirstRun(application: Application): Boolean {
        val shared = application.getSharedPreferences(ConstSharedPref.KUIZ_DB, MODE_PRIVATE)
        val isRun = shared.getBoolean(ConstSharedPref.IS_FIRST_RUN, true)
        return isRun
    }
}