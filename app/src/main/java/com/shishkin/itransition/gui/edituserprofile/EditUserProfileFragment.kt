package com.shishkin.itransition.gui.edituserprofile

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.shishkin.itransition.R
import com.shishkin.itransition.databinding.FragmentEditUserProfileBinding
import com.shishkin.itransition.gui.edituserprofile.imagepickersheetdialog.ImagePickerSheetDialogFragment
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private const val IMAGE_PICKER_SHEET_DIALOG_TAG = "ImagePickerSheetDialogFragmentDialog"
private const val USER_ID = 1

class EditUserProfileFragment : DaggerFragment(), ImageRetriever {

    @Inject
    lateinit var viewModelFactory: EditUserProfileViewModelFactory
    private val viewModel: EditUserProfileViewModel by viewModels { viewModelFactory }

    private lateinit var _binding: FragmentEditUserProfileBinding
    private val binding get() = _binding

    lateinit var profileImageUri: Uri

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

        binding.btnEditUserProfileApply.setOnClickListener {
            val userUi = createUserUi()
            insertUserData(userUi)
            findNavController().navigate(R.id.action_editUserProfileFragment_to_userProfileFragment2)
//            activity?.finish()
//            TODO приходится нажать еще раз на табу User, чтобы обновились textView
        }
    }

    private fun createUserUi(): UserUi {
        val userName = binding.etEditUserProfileName.text.toString()
        val userBirthDate = binding.etEditUserProfileDateChooser.text.toString()
        Timber.tag("UserUi").d("Uri: %s", profileImageUri.toString())
        return UserUi(USER_ID, userName, userBirthDate, profileImageUri.toString())
    }

    private fun insertUserData(userUi: UserUi) {
        lifecycleScope.launch {
            viewModel.insertUser(userUi)
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
        binding.ivEditUserProfileUserImage.setImageURI(imageUri)
        if (imageUri != null) {
            profileImageUri = imageUri
        }
    }
}