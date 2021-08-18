package com.shishkin.itransition.gui.login

import android.os.Bundle
import com.shishkin.itransition.databinding.ActivityLoginBinding
import dagger.android.support.DaggerAppCompatActivity

class LoginActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}