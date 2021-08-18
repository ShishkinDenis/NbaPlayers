package com.shishkin.itransition.extensions

import android.content.Context
import android.widget.Toast

fun Context.showLongToast(message: Int) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()