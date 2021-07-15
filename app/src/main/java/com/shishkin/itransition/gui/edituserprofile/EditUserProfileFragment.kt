package com.shishkin.itransition.gui.edituserprofile

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.shishkin.itransition.R
import com.shishkin.itransition.databinding.FragmentEditUserProfileBinding
import dagger.android.support.DaggerFragment
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

private const val USER_BIRTH_DATE_FORMAT = "dd/MM/yyyy"

class EditUserProfileFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: EditUserProfileViewModelFactory
    val viewModel: EditUserProfileViewModel by viewModels { viewModelFactory }

    private lateinit var _binding: FragmentEditUserProfileBinding
    private val binding get() = _binding
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etEditUserProfileDateChooser.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun updateDateInDateChooser() {
        val sdf = SimpleDateFormat(USER_BIRTH_DATE_FORMAT, Locale.US)
        val chosenDate = calendar.time
        val currentDate = Date()
        val chosenConvertedDate = sdf.format(chosenDate)

        if (chosenDate.before(currentDate)) {
            binding.etEditUserProfileDateChooser.setText(chosenConvertedDate)
        } else {
            Toast.makeText(
                context,
                getString(R.string.edit_user_profile_not_valid_date_toast_message),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun setDateListener(): DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            with(calendar) {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, monthOfYear)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            updateDateInDateChooser()
        }
    }

    private fun showDatePickerDialog() {
        context?.let { context ->
            DatePickerDialog(
                context, setDateListener(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}
