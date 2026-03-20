package com.fera.kuiz.common.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.fera.kuiz.R


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


val categoryIcons = mapOf(
    "Health" to R.drawable.ic_cat_health,
    "Sports" to R.drawable.ic_cat_sport,
    "Science" to R.drawable.ic_cat_science_bold,
    "Logic and Puzzles" to R.drawable.ic_cat_puzzle,
    "Language" to R.drawable.ic_cat_language,
    "Animals" to R.drawable.ic_cat_animal,
    "Papua New Guinea" to R.drawable.ic_cat_papua_new_guinea,
    "History" to R.drawable.ic_cat_history,
    "Art and Culture" to R.drawable.ic_cat_art,
    "Literature" to R.drawable.ic_cat_literature,
    "Mathematics" to R.drawable.ic_cat_maths,
    "Music" to R.drawable.ic_cat_music,
    "Space" to R.drawable.ic_cat_space,
    "Mythology & Legends" to R.drawable.ic_cat_myths,
    "Food & Cuisine" to R.drawable.ic_cat_food,
    "Geography & World Capitals" to R.drawable.ic_cat_geopraphy,
    "Nature & Environment" to R.drawable.ic_cat_nature,
    "Engineering & Inventions" to R.drawable.ic_cat_engineer
)