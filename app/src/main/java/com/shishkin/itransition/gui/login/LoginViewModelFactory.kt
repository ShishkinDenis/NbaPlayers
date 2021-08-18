package com.shishkin.itransition.gui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shishkin.itransition.di.CoroutineContextProvider
import com.shishkin.itransition.di.LoginUserNameValidator
import com.shishkin.itransition.di.LoginUserPasswordValidator
import com.shishkin.itransition.gui.login.mappers.LoginUserUiToLoginUserRemoteMapper
import com.shishkin.itransition.repository.LoginRepository
import com.shishkin.itransition.validators.Validator
import javax.inject.Inject

class LoginViewModelFactory @Inject constructor(
    private val contextProvider: CoroutineContextProvider,
    @LoginUserNameValidator private val userNameValidator: Validator<String>,
    @LoginUserPasswordValidator private val userPasswordValidator: Validator<String>,
    private val loginRepository: LoginRepository,
    private val loginUserUiToLoginUserRemoteMapper: LoginUserUiToLoginUserRemoteMapper
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(
            contextProvider = contextProvider,
            userNameValidator = userNameValidator,
            userPasswordValidator = userPasswordValidator,
            loginRepository = loginRepository,
            loginUserUiToLoginUserRemoteMapper = loginUserUiToLoginUserRemoteMapper
        ) as T
    }
}
