package com.shishkin.itransition.gui.edituserprofile

import android.os.Bundle
import com.shishkin.itransition.databinding.ActivityEditUserProfileBinding
import dagger.android.support.DaggerAppCompatActivity

class EditUserProfileActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivityEditUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditUserProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}