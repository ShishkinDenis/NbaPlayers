package com.shishkin.itransition.gui.nba

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shishkin.itransition.repository.NbaRepository
import javax.inject.Inject

class NbaViewModelFactory @Inject constructor(var nbaRepository: NbaRepository) :
        ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NbaViewModel::class.java)) {
            NbaViewModel(this.nbaRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

    // TODO Evgeny логика create сделать таким образом:
 /*   @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return  NbaViewModel(
         nbaRepository=  nbaRepository
        ) as T
    }*/
}