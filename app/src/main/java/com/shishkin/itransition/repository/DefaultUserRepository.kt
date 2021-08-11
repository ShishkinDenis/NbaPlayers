package com.shishkin.itransition.repository

import android.content.res.Resources
import com.shishkin.itransition.db.UserDao
import com.shishkin.itransition.db.UserLocal
import com.shishkin.itransition.network.entities.KResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(private val userDao: UserDao) : UserRepository {

    override fun getUserFromDb(): Flow<KResult<UserLocal>> {
        return flow<KResult<UserLocal>> {
            userDao.getUser().collect {
                if (it == null) {
                    emit(Result.failure(Resources.NotFoundException()))
                } else {
                    emit(Result.success(it))
                }
            }
        }
    }

    override fun insertUserToDb(userLocal: UserLocal): Flow<KResult<Long>> = flow {
        val id = userDao.insertUser(userLocal)
        emit(KResult.success(id))
    }
}