package com.shishkin.itransition.gui.userprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shishkin.itransition.R
import com.shishkin.itransition.gui.edituserprofile.UserUi
import com.shishkin.itransition.gui.userprofile.mappers.UserLocalToUserUiMapper
import com.shishkin.itransition.navigation.ActivityNavigation
import com.shishkin.itransition.navigation.Navigation
import com.shishkin.itransition.network.entities.ResultState
import com.shishkin.itransition.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userLocalToUserUiMapper: UserLocalToUserUiMapper
) : ViewModel() {

    private val _userState: MutableStateFlow<ResultState<UserUi>> =
        MutableStateFlow(ResultState.loading())

    val userState: StateFlow<ResultState<UserUi>> = _userState

    private val _navigation =
        MutableSharedFlow<Navigation>()
    val navigation = _navigation.asSharedFlow()

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            _userState.value = ResultState.loading()
            withContext(Dispatchers.IO) {
                userRepository.getUserFromDb()
                    .collect { result ->
                        result.fold(
                            onSuccess = { userLocal ->
                                withContext(Dispatchers.Main) {
                                    _userState.value = ResultState.success(
                                        userLocalToUserUiMapper.invoke(userLocal)
                                    )
                                }
                            },
                            onFailure = { error ->
                                _userState.value = ResultState.error(error.message, error)
                            }
                        )
                    }
            }
        }
    }

    fun navigateToEditUserProfileActivity() {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                _navigation.emit(ActivityNavigation(R.id.action_userProfileFragment_to_editUserProfileActivity))
            }
        }
    }
}