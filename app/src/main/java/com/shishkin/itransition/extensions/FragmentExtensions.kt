package com.shishkin.itransition.extensions

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

fun Fragment.handleBackButton(functionCall: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                functionCall()
            }
        })
}

