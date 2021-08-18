package com.shishkin.itransition.repository

import com.shishkin.itransition.network.entities.KResult
import com.shishkin.itransition.network.entities.LoginResponse
import com.shishkin.itransition.network.entities.LoginUserRemote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DefaultLoginRepository : LoginRepository {

    override suspend fun login(loginUserRemote: LoginUserRemote): Flow<KResult<LoginResponse>> {
        return flow {
            val result = Result.success(LoginResponse("Successful", true))
//            TODO for error handling testing
//             val result = Result.failure<LoginResponse>(Exception())
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}

