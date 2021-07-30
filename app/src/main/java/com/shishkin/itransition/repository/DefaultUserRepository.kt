package com.shishkin.itransition.repository

import com.shishkin.itransition.db.UserDao
import com.shishkin.itransition.db.UserLocal
import com.shishkin.itransition.network.entities.KResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(private val userDao: UserDao) : UserRepository {

    override fun getUserFromDb(): Flow<KResult<UserLocal>> {
        return flow {
            emit(KResult.success(userDao.getUser()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun insertUserToDb(userLocal: UserLocal) {
        userDao.insertUser(userLocal)
    }
}