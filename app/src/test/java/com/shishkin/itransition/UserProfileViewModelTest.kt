package com.shishkin.itransition

import android.net.Uri
import com.google.common.truth.Truth
import com.shishkin.itransition.db.UserLocal
import com.shishkin.itransition.gui.edituserprofile.UserUi
import com.shishkin.itransition.gui.userprofile.UserProfileViewModel
import com.shishkin.itransition.gui.userprofile.mappers.UserLocalToUserUiMapper
import com.shishkin.itransition.navigation.ActivityNavigation
import com.shishkin.itransition.network.entities.KResult
import com.shishkin.itransition.network.entities.ResultState
import com.shishkin.itransition.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserProfileViewModelTest {

    private lateinit var testContextProvider: TestContextProvider
    private lateinit var viewModel: UserProfileViewModel

    @Mock
    private lateinit var userLocalToUserUiMapper: UserLocalToUserUiMapper

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var uri: Uri

    @Before
    fun setUp() {
        testContextProvider = TestContextProvider()
        viewModel =
            UserProfileViewModel(
                userRepository,
                userLocalToUserUiMapper,
                testContextProvider
            )
    }

    @Test
    fun getUserFromDbWasCalled() = runBlockingTest {
        verify(userRepository).getUserFromDb()
    }

    //TODO Exception in thread "main @coroutine#1" java.lang.NullPointerException
    @Test
    fun playersStateEmitsSuccess() = runBlockingTest {
        val fakeInt = 1
        val fakeString = "a"
        val fakeUserUi = UserUi(fakeInt, fakeString, fakeString, uri)
        val fakeUserLocal = UserLocal(fakeInt, fakeString, fakeString, fakeString)
        whenever(userRepository.getUserFromDb()).thenReturn(flowOf(KResult.success(fakeUserLocal)))
        whenever(userLocalToUserUiMapper.invoke(fakeUserLocal)).thenReturn(fakeUserUi)
        viewModel.loadUser()
        Truth.assertThat(viewModel.userState.value.status)
            .isEqualTo(ResultState.success(fakeUserUi).status)
    }

    //TODO Exception in thread "main @coroutine#1" java.lang.NullPointerException
    @Test
    fun playersStateEmitsFailure() = runBlockingTest {
        val error = NullPointerException()
        whenever(userRepository.getUserFromDb()).thenReturn(flowOf(KResult.failure(error)))
        viewModel.loadUser()
        Truth.assertThat(viewModel.userState.value.status)
            .isEqualTo(ResultState.error<UserUi>(error.message, error).status)
    }

    @Test
    fun navigationEmitsActivityNavigation() = runBlockingTest {
        launch {
            viewModel.navigateToEditUserProfileActivity()
            Truth.assertThat(viewModel.navigation.first())
                .isEqualTo(ActivityNavigation(R.id.action_userProfileFragment_to_editUserProfileActivity))
        }.cancel()
    }
}