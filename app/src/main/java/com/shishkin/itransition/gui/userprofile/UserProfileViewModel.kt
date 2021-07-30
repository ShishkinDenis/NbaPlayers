package com.shishkin.itransition.gui.userprofile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shishkin.itransition.gui.edituserprofile.UserUi
import com.shishkin.itransition.gui.userprofile.mappers.UserLocalToUserUiMapper
import com.shishkin.itransition.network.entities.ResultState
import com.shishkin.itransition.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
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

    init {
        loadUser()
    }

    private fun loadUser() {
        Log.e("JEKA", "Load user called")
        viewModelScope.launch {
            _userState.value = ResultState.loading()
            withContext(Dispatchers.IO) {
              Log.e("JEKA", "Get user from DB")
                userRepository.getUserFromDb()
                  .collect { result ->
                      result.fold(
                        onSuccess = { userLocal ->
                            Log.e("JEKA", "Get success: "+userLocal)
                            withContext(Dispatchers.Main) {
                                _userState.value = ResultState.success(
                                  userLocalToUserUiMapper.invoke(userLocal)
                                )
                            }
                        },
                        onFailure = { error ->
                            Log.e("JEKA", "Get error: "+error)
                            _userState.value = ResultState.error(error.message, error)
                        }
                      )
                  }
            }
        }
    }

  fun navigateToUserProfileActivity() {
    // TODO: прокидывать navigation во фрагмент.
  }
}