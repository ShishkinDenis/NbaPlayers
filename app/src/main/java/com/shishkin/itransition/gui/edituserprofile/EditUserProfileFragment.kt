package com.shishkin.itransition.gui.edituserprofile

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.shishkin.itransition.R
import com.shishkin.itransition.databinding.FragmentEditUserProfileBinding
import com.shishkin.itransition.extensions.makeGone
import com.shishkin.itransition.extensions.makeVisible
import com.shishkin.itransition.gui.edituserprofile.imagepickersheetdialog.ImagePickerSheetDialogFragment
import com.shishkin.itransition.navigation.BaseNavigationEmitter
import com.shishkin.itransition.navigation.NavigationEmitter
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val IMAGE_PICKER_SHEET_DIALOG_TAG = "ImagePickerSheetDialogFragmentDialog"

class EditUserProfileFragment : DaggerFragment(), ImageRetriever {

    @Inject
    lateinit var viewModelFactory: EditUserProfileViewModelFactory
    private val viewModel: EditUserProfileViewModel by viewModels { viewModelFactory }

    private lateinit var _binding: FragmentEditUserProfileBinding
    private val binding get() = _binding

    private val navigationEmitter: NavigationEmitter by lazy {
        BaseNavigationEmitter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        initEditTextListeners()
        collectData()
        enableApplyButtonIfValid()
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.toast.collect { toastMessage ->
                        showToast(toastMessage)
                    }
                }

                launch {
                    viewModel.navigation.collect { navigation ->
                        navigationEmitter.navigateTo(navigation)
                    }
                }

                launch {
                    viewModel.userState.collect { uiState ->
                        updateUi(uiState)
                    }
                }

                launch {
                    viewModel.progress.collect { progressBarState ->
                        showProgressBar(progressBarState)
                    }
                }

                launch {
                    viewModel.userNameError.collect { nameState ->
                        showErrorInUserProfileNameField(nameState)
                    }
                }

                launch {
                    viewModel.userBirthDateError.collect { birthDateState ->
                        showErrorInUserBirthDateField(birthDateState)
                    }
                }
            }
        }
    }

    private fun showProgressBar(progressBarState: Boolean) {
        if (progressBarState) {
            binding.pbEditUserProfile.makeVisible()
        } else {
            binding.pbEditUserProfile.makeGone()
        }
    }

    private fun showErrorInUserProfileNameField(nameIsValid: Boolean) {
        if (nameIsValid) {
            binding.tilEditUserProfileName.error = null
        } else {
            binding.tilEditUserProfileName.error =
                getString(R.string.edit_user_profile_name_should_contain_at_least_four_characters_error)
        }
    }

    private fun showErrorInUserBirthDateField(birthDateIsValid: Boolean) {
        if (birthDateIsValid) {
            binding.tilEditUserProfileDateChooser.error = null
        } else {
            binding.tilEditUserProfileDateChooser.error =
                getString(R.string.edit_user_profile_please_input_birth_date_error)
        }
    }

    private fun setClickListeners() {
        binding.etEditUserProfileDateChooser.setOnClickListener {
            showDatePickerDialog()
        }
        binding.ivEditUserProfileUserImage.setOnClickListener {
            showImagePickerBottomSheetDialog()
        }
        binding.btnEditUserProfileApply.setOnClickListener {
            viewModel.insertUser()
        }
    }

    private fun showImagePickerBottomSheetDialog() {
        val fragment = childFragmentManager.findFragmentByTag(IMAGE_PICKER_SHEET_DIALOG_TAG)
        if (fragment == null) {
            val imagePickerSheetDialogFragment = ImagePickerSheetDialogFragment.createNewInstance()
            imagePickerSheetDialogFragment.show(childFragmentManager, IMAGE_PICKER_SHEET_DIALOG_TAG)
        }
    }

    private fun showDatePickerDialog() {
        val config = viewModel.getUserDate()
        context?.let { context ->
            DatePickerDialog(
                context, { _, year, monthOfYear, dayOfMonth ->
                    viewModel.setUserDate(
                        DatePickerConfig(
                            day = dayOfMonth,
                            month = monthOfYear,
                            year = year
                        )
                    )
                },
                config.year,
                config.month,
                config.day
            ).show()
        }
    }

    private fun showToast(toastMessage: Int) {
        Toast.makeText(
            context,
            toastMessage,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onRetrieveImage(imageUri: Uri?) {
        if (imageUri != null) {
            viewModel.setProfileImageUri(imageUri)
        }
    }

    private fun updateUi(userUi: UserUi?) {
        if (binding.etEditUserProfileName.text.toString() != userUi?.name) {
            binding.etEditUserProfileName.setText(userUi?.name ?: "")
        }
        binding.etEditUserProfileDateChooser.setText(userUi?.birthDate)

        userUi?.profileImageUri?.let {
            context?.let { context ->
                Glide
                    .with(context)
                    .load(it)
                    .into(binding.ivEditUserProfileUserImage)
            }
        }
    }

    private fun initEditTextListeners() {
        binding.etEditUserProfileName.addTextChangedListener {
            viewModel.setUserName(it.toString())
        }
        binding.etEditUserProfileDateChooser.addTextChangedListener {
            viewModel.setUserBirthDate(it.toString())
        }
    }

    private fun enableApplyButtonIfValid() {
        binding.btnEditUserProfileApply.isEnabled = true
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.applyButton.collect { value ->
                    binding.btnEditUserProfileApply.isEnabled = value
                }
            }
        }
    }
}