package com.shishkin.itransition.navigation

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class BaseNavigationEmitter(private val fragment: Fragment) : NavigationEmitter {

    override fun navigateTo(navigation: Navigation) {
        when (navigation) {
            is FinishActivityNavigation -> {
                fragment.activity?.finish()
            }
            is ActivityNavigation -> {
                fragment.findNavController().navigate(navigation.resId)
            }
        }
    }
}