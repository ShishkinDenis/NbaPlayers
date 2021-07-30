package com.shishkin.itransition.gui.userprofile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.shishkin.itransition.R
import com.shishkin.itransition.databinding.FragmentUserProfileBinding
import com.shishkin.itransition.gui.edituserprofile.UserUi
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


private const val USER_UI_TAG = "UserUi"

class UserProfileFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: UserProfileViewModelFactory
    private val viewModel: UserProfileViewModel by viewModels { viewModelFactory }

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

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userState.collectLatest { uiState ->
                    uiState.fold(
                        onLoading = {
                            Timber.tag(USER_UI_TAG).d(getString(R.string.user_profile_loading))
                        },
                        onSuccess = { userUi ->
                            userUi?.let {
                                updateUi(userUi)
                            }
                        },
                        onError = { _, message ->
                            Timber.tag(USER_UI_TAG).e(
                                getString(R.string.user_profile_error),
                                message
                            )
                        }
                    )
                }
            }
        }
    }

    private fun updateUi(userUi: UserUi?) {
        binding.tvUserProfileUserName.text = userUi?.name
        binding.tvUserProfileBirthDate.text = userUi?.birthDate
        val profileImageUri = Uri.parse(userUi?.profileImageUri)
        Timber.tag(USER_UI_TAG).d("Got Uri: %s", profileImageUri.toString())
        context?.let { context ->
            Glide
                .with(context)
                .load(profileImageUri)
                .into(binding.ivUserProfileUserImage)
        }
    }
}