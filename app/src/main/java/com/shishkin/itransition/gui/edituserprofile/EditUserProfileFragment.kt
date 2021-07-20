package com.shishkin.itransition.gui.edituserprofile

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.shishkin.itransition.databinding.FragmentEditUserProfileBinding
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val IMAGE_PICKER_SHEET_DIALOG_TAG = "ImagePickerSheetDialogFragmentDialog"

class EditUserProfileFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: EditUserProfileViewModelFactory
    private val viewModel: EditUserProfileViewModel by viewModels { viewModelFactory }

    private lateinit var _binding: FragmentEditUserProfileBinding
    private val binding get() = _binding

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
    }

    private fun showImagePickerBottomSheetDialog() {
        val imagePickerSheetDialogFragment = ImagePickerSheetDialogFragment()
        imagePickerSheetDialogFragment.show(parentFragmentManager, IMAGE_PICKER_SHEET_DIALOG_TAG)
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
}