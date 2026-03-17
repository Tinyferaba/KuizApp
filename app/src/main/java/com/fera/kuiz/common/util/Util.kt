package com.fera.kuiz.common.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast


fun Context.toastShort(msg: String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.toastLong(msg: String){
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun generatePrimaryKey(): Long {
    val timestamp = System.currentTimeMillis()
    val randomSuffix = (0..500).random() // Generate a random number between 0 and 99
    return (timestamp * 100) + randomSuffix
}


fun showKeyboard(context: Context, view: View) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    view.requestFocus()
    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun hideKeyboard(context: Context, view: View) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
    view.clearFocus()
}