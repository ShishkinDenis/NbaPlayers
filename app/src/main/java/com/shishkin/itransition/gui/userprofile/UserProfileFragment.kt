package com.shishkin.itransition.gui.userprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shishkin.itransition.R
import com.shishkin.itransition.databinding.FragmentUserProfileBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class UserProfileFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: UserProfileViewModelFactory
    val viewModel: UserProfileViewModel by viewModels { viewModelFactory }

    private lateinit var _binding: FragmentUserProfileBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivUserProfileEditProfileButton.setOnClickListener {
            findNavController().navigate(R.id.action_userProfileFragment_to_editUserProfileActivity)
        }
    }
}