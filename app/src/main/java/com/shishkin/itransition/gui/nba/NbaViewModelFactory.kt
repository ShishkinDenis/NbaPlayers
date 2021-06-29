package com.shishkin.itransition.gui.nba

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shishkin.itransition.repository.NbaRepository
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@InternalCoroutinesApi
class NbaViewModelFactory @Inject constructor(var nbaRepository: NbaRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NbaViewModel::class.java)) {
            NbaViewModel(this.nbaRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}