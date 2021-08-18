package com.shishkin.itransition.gui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shishkin.itransition.R
import com.shishkin.itransition.di.CoroutineContextProvider
import com.shishkin.itransition.di.LoginUserNameValidator
import com.shishkin.itransition.di.LoginUserPasswordValidator
import com.shishkin.itransition.gui.login.mappers.LoginUserUiToLoginUserRemoteMapper
import com.shishkin.itransition.navigation.ActivityNavigation
import com.shishkin.itransition.navigation.FinishActivityNavigation
import com.shishkin.itransition.navigation.Navigation
import com.shishkin.itransition.repository.LoginRepository
import com.shishkin.itransition.validators.Validator
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val contextProvider: CoroutineContextProvider,
    @LoginUserNameValidator private val userNameValidator: Validator<String>,
    @LoginUserPasswordValidator private val userPasswordValidator: Validator<String>,
    private val loginRepository: LoginRepository,
    private val loginUserUiToLoginUserRemoteMapper: LoginUserUiToLoginUserRemoteMapper
) : ViewModel() {

    private val toastData = MutableSharedFlow<Int>()
    val toast = toastData.asSharedFlow()

    private val navigationData = MutableSharedFlow<Navigation>()
    val navigation = navigationData.asSharedFlow()

    private val userNameErrorData = MutableStateFlow(false)
    val userNameError = userNameErrorData.asStateFlow()

    private val userPasswordErrorData = MutableStateFlow(false)
    val userPasswordError = userPasswordErrorData.asStateFlow()

    private val loginUserStateData: MutableStateFlow<LoginUserUi> =
        MutableStateFlow(getEmptyLoginUserUi())

    init {
        subscribeOnLoginUserUiAndValidate()
    }

    fun setUserName(name: String) {
        loginUserStateData.tryEmit(
            loginUserStateData.value.copy(
                name = name
            )
        )
    }

    fun setUserPassword(password: String) {
        loginUserStateData.tryEmit(
            loginUserStateData.value.copy(
                password = password
            )
        )
    }

    private fun getEmptyLoginUserUi(): LoginUserUi = LoginUserUi(
        name = "",
        password = ""
    )

    private fun subscribeOnLoginUserUiAndValidate() {
        viewModelScope.launch(contextProvider.io) {
            loginUserStateData.collect { loginUserUi ->
                userNameErrorData.emit(
                    userNameValidator.validate(loginUserUi.name)
                )
                userPasswordErrorData.emit(
                    userPasswordValidator.validate(loginUserUi.password)
                )
            }
        }
    }

    val loginButton: Flow<Boolean> =
        combine(
            userNameErrorData,
            userPasswordErrorData
        ) { userName, userPassword ->
            return@combine userName and userPassword
        }

    fun login() {
        viewModelScope.launch(contextProvider.io) {
            val loginUserRemote =
                loginUserUiToLoginUserRemoteMapper.invoke(loginUserStateData.value)
            loginRepository.login(loginUserRemote)
                .collect { result ->
                    result.fold(
                        onSuccess = {
                            if (it.status) {
                                withContext(contextProvider.main) {
                                    navigateToMainActivity()
                                }
                            }
                        },
                        onFailure = {
                            emitToastMessage(R.string.login_login_failed)
                        }
                    )
                }
        }
    }

    private fun emitToastMessage(toastMessage: Int) {
        viewModelScope.launch {
            toastData.emit(toastMessage)
        }
    }

    private fun navigateToMainActivity() {
        viewModelScope.launch(contextProvider.main) {
            navigationData.emit(FinishActivityNavigation)
            navigationData.emit(ActivityNavigation(R.id.action_loginFragment_to_mainActivity))
        }
    }
}