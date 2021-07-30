package com.shishkin.itransition.gui.edituserprofile

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import java.lang.Exception
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

        lifecycleScope.launchWhenStarted {
            viewModel.date.collect { date ->
                binding.etEditUserProfileDateChooser.setText(date)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.navigation.collect { navigation ->
                navigationEmitter.navigateTo(navigation)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.toast.collect { toastMessage ->
                showToast(toastMessage)
            }
        }

        binding.btnEditUserProfileApply.setOnClickListener {
            try {
                val userUi = createUserUi()
                insertUserData(userUi)
            } catch (e: Exception) {
                Log.e("JEKA", "Error: "+e)
            }
        }
    }

    private fun createUserUi(): UserUi {
        val userName = binding.etEditUserProfileName.text.toString()
        val userBirthDate = binding.etEditUserProfileDateChooser.text.toString()
        Timber.tag("UserUi").d("Uri: %s", profileImageUri.toString())
        return UserUi(USER_ID, userName, userBirthDate, profileImageUri.toString())
    }

    private fun insertUserData(userUi: UserUi) {
            Log.e("JEKA", "Inser user: "+userUi)
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
            profileImageUri = imageUri
        }
    }
}