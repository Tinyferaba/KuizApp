package com.fera.kuiz.common.util

import android.content.Context
import android.widget.Toast

fun Context.toastShort(msg: String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT)
}

fun Context.toastLong(msg: String){
    Toast.makeText(this, msg, Toast.LENGTH_LONG)
}


