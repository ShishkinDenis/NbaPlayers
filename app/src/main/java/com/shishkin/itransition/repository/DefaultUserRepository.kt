package com.shishkin.itransition.repository

import android.util.Log
import com.shishkin.itransition.db.UserDao
import com.shishkin.itransition.db.UserLocal
import com.shishkin.itransition.network.entities.KResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

class NotFoundException: Exception()

class DefaultUserRepository @Inject constructor(private val userDao: UserDao) : UserRepository {

    override fun getUserFromDb(): Flow<KResult<UserLocal>> {
        return flow {
          userDao.getUser().collect {
            if (it == null) {
              emit(Result.failure<UserLocal>(NotFoundException()))
            } else {
              emit(Result.success(it))
            }
          }
        }
    }

  override fun insertUserToDb(userLocal: UserLocal): Flow<KResult<Long>> = flow {
    val id = userDao.insertUser(userLocal)
    Log.e("JEKA", "User inserted: "+id)
    emit(KResult.success(id))
  }
}