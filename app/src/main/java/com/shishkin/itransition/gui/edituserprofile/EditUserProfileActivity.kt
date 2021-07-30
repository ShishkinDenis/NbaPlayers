package com.shishkin.itransition.gui.edituserprofile

import android.os.Bundle
import androidx.navigation.findNavController
import com.shishkin.itransition.R
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

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.edit_user_profile_host_fragment).navigateUp()
//         super.onSupportNavigateUp()
    }
}