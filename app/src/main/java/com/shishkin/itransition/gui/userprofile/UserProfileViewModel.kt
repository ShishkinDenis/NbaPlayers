package com.shishkin.itransition.gui.userprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shishkin.itransition.gui.edituserprofile.UserUi
import com.shishkin.itransition.gui.userprofile.mappers.UserLocalToUserUiMapper
import com.shishkin.itransition.network.entities.ResultState
import com.shishkin.itransition.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userLocalToUserUiMapper: UserLocalToUserUiMapper
) : ViewModel() {

    private val _userState: MutableStateFlow<ResultState<UserUi>> =
        MutableStateFlow(ResultState.loading())

    val userState: StateFlow<ResultState<UserUi>> = _userState

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            _userState.value = ResultState.loading()
            userRepository
            userRepository.getUserFromDb()
                .catch { e -> _userState.value = ResultState.error(e.message, e) }
                .collect { result ->
                    result.fold(
                        onSuccess = { userLocal ->
                            _userState.value = ResultState.success(
                                userLocalToUserUiMapper.invoke(userLocal)
                            )
                        },
                        onFailure = { error ->
                            _userState.value = ResultState.error(error.message, error)
                        }
                    )
                }
        }
    }
}