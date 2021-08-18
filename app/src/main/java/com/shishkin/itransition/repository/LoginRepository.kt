package com.shishkin.itransition.repository

import com.shishkin.itransition.network.entities.KResult
import com.shishkin.itransition.network.entities.LoginResponse
import com.shishkin.itransition.network.entities.LoginUserRemote
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

   suspend fun login(loginUserRemote: LoginUserRemote): Flow<KResult<LoginResponse>>
}