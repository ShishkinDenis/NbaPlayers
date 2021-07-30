package com.shishkin.itransition.repository

import com.shishkin.itransition.db.UserLocal
import com.shishkin.itransition.network.entities.KResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUserFromDb(): Flow<KResult<UserLocal>>

    fun insertUserToDb(userLocal: UserLocal): Flow<KResult<Long>>
}