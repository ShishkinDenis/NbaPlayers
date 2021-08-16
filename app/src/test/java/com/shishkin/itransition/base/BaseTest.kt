package com.shishkin.itransition.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
abstract class BaseTest {

  @get:Rule
  val testCoroutineRule = TestCoroutineRule()

  @get:Rule
  val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
}