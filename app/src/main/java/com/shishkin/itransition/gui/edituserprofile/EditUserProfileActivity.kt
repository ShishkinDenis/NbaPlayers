package com.shishkin.itransition.gui.edituserprofile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shishkin.itransition.databinding.ActivityEditUserProfileBinding

class EditUserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditUserProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}