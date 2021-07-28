package com.shishkin.itransition.workers

import android.util.Log
import javax.inject.Inject

class TestLogger @Inject constructor() {

    fun log() {
        Log.d("TestLogger", "This is TestLogger")
    }
}