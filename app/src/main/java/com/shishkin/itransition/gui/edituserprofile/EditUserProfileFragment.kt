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
import com.shishkin.itransition.gui.edituserprofile.imagepickersheetdialog.ImagePickerSheetDialogFragment
import com.shishkin.itransition.gui.userprofile.USER_UI_TAG
import com.shishkin.itransition.navigation.BaseNavigationEmitter
import com.shishkin.itransition.navigation.NavigationEmitter
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
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

        binding.etEditUserProfileDateChooser.setOnClickListener {
            showDatePickerDialog()
        }
        binding.ivEditUserProfileUserImage.setOnClickListener {
            showImagePickerBottomSheetDialog()
        }
        binding.btnEditUserProfileApply.setOnClickListener {
            lifecycleScope.launch {
                viewModel.user.collect {
                    insertUserData(it)
                }
            }
        }
        initEditTextListeners()

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.date.collect { date ->
                    binding.etEditUserProfileDateChooser.setText(date)
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.toast.collect { toastMessage ->
                    showToast(toastMessage)
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigation.collect { navigation ->
                    navigationEmitter.navigateTo(navigation)
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userState.collectLatest { uiState ->
                    uiState.fold(
                        onLoading = {
                            Timber.tag(USER_UI_TAG).d(getString(R.string.edit_user_profile_loading))
                        },
                        onSuccess = { userUi ->
                            userUi?.let {
                                updateUi(userUi)
                            }
                        },
                        onError = { _, message ->
                            Timber.tag(USER_UI_TAG).e(
                                getString(R.string.edit_user_profile_error),
                                message
                            )
                        }
                    )
                }
            }
        }
        enableApplyButtonIfValid()
    }

    private fun insertUserData(userUi: UserUi) {
        viewModel.insertUser(userUi)
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
        binding.ivEditUserProfileUserImage.setImageURI(imageUri)
        if (imageUri != null) {
            viewModel.setProfileImageUri(imageUri.toString())
        }
    }

    private fun updateUi(userUi: UserUi?) {
        binding.etEditUserProfileName.setText(userUi?.name)
        binding.etEditUserProfileDateChooser.setText(userUi?.birthDate)
        val profileImageUri = Uri.parse(userUi?.profileImageUri)
        context?.let { context ->
            Glide
                .with(context)
                .load(profileImageUri)
                .into(binding.ivEditUserProfileUserImage)
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
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isApplyButtonEnabled.collect { value ->
                    binding.btnEditUserProfileApply.isEnabled = value
                }
            }
        }
    }
}