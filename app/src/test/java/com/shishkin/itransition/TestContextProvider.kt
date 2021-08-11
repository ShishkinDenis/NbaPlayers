package com.shishkin.itransition

import com.shishkin.itransition.di.CoroutineContextProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class TestContextProvider: CoroutineContextProvider() {
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    override val main: CoroutineContext = testCoroutineDispatcher
    override val io: CoroutineContext = testCoroutineDispatcher
}