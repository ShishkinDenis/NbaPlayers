package com.shishkin.itransition.gui.nba

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shishkin.itransition.NbaPlayersUiState
import com.shishkin.itransition.NbaPlayersUiState.Success
import com.shishkin.itransition.db.NbaPlayerData
import com.shishkin.itransition.repository.NbaPlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class NbaViewModel @Inject constructor(var nbaPlayerRepository: NbaPlayerRepository) : ViewModel() {


     var nbaPlayerData: NbaPlayerData = NbaPlayerData()

    private val _uiState = MutableStateFlow(Success(nbaPlayerData))

    val uiState: StateFlow<NbaPlayersUiState> = _uiState

    init {
        viewModelScope.launch {
            nbaPlayerRepository.getNbaPlayersData()
                .collect { nbaPlayers ->
                    _uiState.value = Success(nbaPlayers)
                }
        }
    }

//        TODO Delete
    //     Coroutines + LiveData
//    val fetchData = liveData(Dispatchers.IO){
//        val data =  nbaPlayerRepository.getData()
//        emit(data)
//    }

    // Call
//    fun fetchNbaPlayersData() {
//        nbaPlayerRepository.getNbaPlayersData()
//    }


}