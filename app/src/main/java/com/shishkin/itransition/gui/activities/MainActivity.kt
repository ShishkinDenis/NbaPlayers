package com.shishkin.itransition.gui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shishkin.itransition.R
import dagger.android.support.DaggerAppCompatActivity


class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

