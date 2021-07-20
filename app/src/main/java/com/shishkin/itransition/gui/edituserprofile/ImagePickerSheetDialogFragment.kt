package com.shishkin.itransition.gui.edituserprofile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shishkin.itransition.R
import com.shishkin.itransition.databinding.FragmentImagePickerSheetDialogBinding
import timber.log.Timber

private const val BOTTOM_SHEET_DIALOG = "BottomSheetDialog"
private const val CAMERA_PERMISSION_ID = 200

class ImagePickerSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var _binding: FragmentImagePickerSheetDialogBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImagePickerSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvImagePickerBottomSheetDialogImageFromCamera.setOnClickListener {
            if (checkIfCameraPermissionIsGranted()) {
                Timber.tag(BOTTOM_SHEET_DIALOG)
                    .d(getString(R.string.image_picker_bottom_sheet_dialog_image_from_camera))
                openCamera()
            } else {
                Timber.tag(BOTTOM_SHEET_DIALOG)
                    .d("Request permission")
                requestCameraPermission()
            }
        }
        binding.tvImagePickerBottomSheetDialogImageFromGallery.setOnClickListener {
            Timber.tag(BOTTOM_SHEET_DIALOG)
                .d(getString(R.string.image_picker_bottom_sheet_dialog_image_from_gallery))
            openGallery()
        }
    }

    companion object {
        fun createNewInstance() = ImagePickerSheetDialogFragment()
    }

    private fun requestCameraPermission() {
        activity?.let { fragmentActivity ->
            ActivityCompat.requestPermissions(
                fragmentActivity, arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.CAMERA
                ), CAMERA_PERMISSION_ID
            )
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivity(intent)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivity(intent)
    }

    private fun checkIfCameraPermissionIsGranted(): Boolean {
        return context?.let { context ->
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            )
        } == PackageManager.PERMISSION_GRANTED
    }
}