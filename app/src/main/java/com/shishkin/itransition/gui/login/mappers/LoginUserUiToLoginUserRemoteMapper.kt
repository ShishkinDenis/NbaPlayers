package com.shishkin.itransition.gui.login.mappers

import com.shishkin.itransition.gui.login.LoginUserUi
import com.shishkin.itransition.gui.utils.Mapper
import com.shishkin.itransition.network.entities.LoginUserRemote
import javax.inject.Inject

class LoginUserUiToLoginUserRemoteMapper @Inject constructor() :
    Mapper<LoginUserUi, LoginUserRemote> {

    override fun invoke(loginUserUi: LoginUserUi): LoginUserRemote {
        return LoginUserRemote(
            name = loginUserUi.name,
            password = loginUserUi.password
        )
    }
}