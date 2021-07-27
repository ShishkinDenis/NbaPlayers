package com.shishkin.itransition.gui.edituserprofile.imagepickersheetdialog

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.work.WorkInfo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shishkin.itransition.R
import com.shishkin.itransition.databinding.FragmentImagePickerSheetDialogBinding
import com.shishkin.itransition.extensions.makeVisible
import com.shishkin.itransition.gui.edituserprofile.ImageRetriever
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private const val CAMERA_PERMISSION_ID = 200
const val WORK_MANAGER_TAG_OUTPUT = "OUTPUT"
const val COMPRESSED_IMAGE_OUTPUT_PATH = "compressed_outputs"
const val OUTPUT_FILE_NAME = "compressed_user_profile_image.png"
const val IMAGE_QUALITY = 0

class ImagePickerSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var _binding: FragmentImagePickerSheetDialogBinding
    private val binding get() = _binding

    @Inject
    lateinit var viewModelFactory: ImagePickerSheetDialogViewModelFactory

    private val viewModel: ImagePickerSheetDialogViewModel by viewModels { viewModelFactory }

    private var inputImageUri: Uri? = null

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
                openCamera()
            } else {
                requestCameraPermission()
            }
        }
        binding.tvImagePickerBottomSheetDialogImageFromGallery.setOnClickListener {
            openGallery()
        }
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.outputWorkInfos.asFlow().collect {
                    Timber.tag("outputWorkInfos").d(it.get(0).toString())
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    private fun customWorkInfosObserver(): Observer<List<WorkInfo>> {
        return Observer { listOfWorkInfo ->
            viewModel.processWorkInfos(listOfWorkInfo)
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        (parentFragment as? ImageRetriever)?.onRetrieveImage(inputImageUri)
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

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                displayPreview()
            }
        }

    private fun openCamera() {
        val values = ContentValues()
        values.put(
            MediaStore.Images.Media.TITLE,
            getString(R.string.image_picker_bottom_sheet_dialog_new_picture)
        )
        values.put(
            MediaStore.Images.Media.DESCRIPTION,
            getString(R.string.image_picker_bottom_sheet_dialog_image_from_camera)
        )
        inputImageUri =
            context?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, inputImageUri)
        cameraLauncher.launch(cameraIntent)
    }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                inputImageUri = result.data?.data
                inputImageUri?.let { viewModel.compressImageWithWorker(it) }
                displayPreview()
            }
        }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(galleryIntent)
    }

    private fun checkIfCameraPermissionIsGranted(): Boolean {
        return context?.let { context ->
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        } == PackageManager.PERMISSION_GRANTED
    }

    private fun displayPreview() {
        with(binding) {
            tvImagePickerBottomSheetDialogChosenImageTitle.makeVisible()
            ivImagePickerBottomSheetDialogChosenImage.makeVisible()
            ivImagePickerBottomSheetDialogChosenImage.setImageURI(inputImageUri)
        }
    }

    companion object {
        fun createNewInstance() = ImagePickerSheetDialogFragment()
    }
}