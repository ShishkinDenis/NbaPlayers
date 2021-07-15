package com.shishkin.itransition.gui.edituserprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.shishkin.itransition.databinding.FragmentEditUserProfileBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class EditUserProfileFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: EditUserProfileViewModelFactory
    val viewModel: EditUserProfileViewModel by viewModels { viewModelFactory }

    private lateinit var _binding: FragmentEditUserProfileBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}