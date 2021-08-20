package com.shishkin.itransition.gui.nba

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shishkin.itransition.di.CoroutineContextProvider
import com.shishkin.itransition.gui.nba.mappers.PlayerWithTeamToNbaPlayerUiMapper
import com.shishkin.itransition.gui.nba.uientities.NbaPlayerUi
import com.shishkin.itransition.network.entities.NbaPlayerRemote
import com.shishkin.itransition.network.entities.ResultState
import com.shishkin.itransition.repository.NbaRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class NbaViewModel @Inject constructor(
    private val nbaRepository: NbaRepository,
    private val playerWithTeamToNbaPlayerUiMapper: PlayerWithTeamToNbaPlayerUiMapper,
    private val contextProvider: CoroutineContextProvider,
) : ViewModel() {

    private val _playersState: MutableStateFlow<ResultState<List<NbaPlayerUi>>> =
        MutableStateFlow(ResultState.loading())
    val playersState: StateFlow<ResultState<List<NbaPlayerUi>>> = _playersState

    //TODO переделать на NbaPlayerUi
    private val nbaPlayersStateDataRX: BehaviorSubject<Result<List<NbaPlayerRemote>?>> =
        BehaviorSubject.create()

    init {
        loadPlayers()
        loadPlayersRX()
    }

    fun loadPlayers() {
        viewModelScope.launch(contextProvider.io) {
            _playersState.value = ResultState.loading()
            nbaRepository.getNbaPlayersList()
                .catch { e -> _playersState.value = ResultState.error(e.message, e) }
                .collect { nbaPlayers ->
                    nbaPlayers.fold(
                        onSuccess = { list ->
                            _playersState.value = ResultState.success(
                                playerWithTeamToNbaPlayerUiMapper.invoke(list)
                            )
                        },
                        onFailure = { error ->
                            _playersState.value = ResultState.error(error.message, error)
                        }
                    )
                }
        }
    }

    fun subscribeOnPlayerState(): Observable<Result<List<NbaPlayerRemote>?>> {
        return nbaPlayersStateDataRX.observeOn(AndroidSchedulers.mainThread())
    }

    private fun loadPlayersRX() {
        nbaRepository.getNbaPlayersListRX()
            .subscribeOn(Schedulers.io())
            ?.doOnError {
                nbaPlayersStateDataRX.onNext(Result.failure(it))
            }
            ?.subscribe { it ->
                it.fold(
                    onSuccess = {
                        Timber.tag("RX").d(it[0].firstName)
//                        TODO промапить на NbaPlayerUi
                        nbaPlayersStateDataRX.onNext(Result.success(it))
                    },
                    onFailure = { error ->
                        Timber.tag("RX").d(error)
                    }
                )
            }
    }
}
