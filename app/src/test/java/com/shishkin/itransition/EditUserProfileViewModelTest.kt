package com.shishkin.itransition

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.shishkin.itransition.base.BaseTest
import com.shishkin.itransition.db.UserLocal
import com.shishkin.itransition.gui.edituserprofile.DatePickerConfig
import com.shishkin.itransition.gui.edituserprofile.EditUserProfileViewModel
import com.shishkin.itransition.gui.edituserprofile.UserUi
import com.shishkin.itransition.gui.edituserprofile.mappers.DateToStringMapper
import com.shishkin.itransition.gui.edituserprofile.mappers.StringToDateMapper
import com.shishkin.itransition.gui.userprofile.mappers.UserLocalToUserUiMapper
import com.shishkin.itransition.gui.userprofile.mappers.UserUiToUserLocalMapper
import com.shishkin.itransition.gui.utils.Mapper
import com.shishkin.itransition.navigation.FinishActivityNavigation
import com.shishkin.itransition.network.entities.KResult
import com.shishkin.itransition.repository.UserRepository
import com.shishkin.itransition.validators.Validator
import com.shishkin.itransition.validators.rules.ChosenDateBeforeCurrentDateRule
import com.shishkin.itransition.validators.rules.DateIsNotNullRule
import com.shishkin.itransition.validators.rules.TextMinLengthRule
import com.shishkin.itransition.validators.rules.TextWithoutDigitsRule
import com.shishkin.itransition.validators.rules.UriIsNotNullRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.whenever
import java.util.*

private const val NOT_VALID_DATE = "Not valid date"

private typealias ValidationRule<T> = com.shishkin.itransition.validators.rules.Rule<T>

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class EditUserProfileViewModelTest : BaseTest() {


    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var uri: Uri

    private lateinit var viewModel: EditUserProfileViewModel
    private lateinit var testContextProvider: TestContextProvider


    @Before
    fun setUp() {
        `when`(userRepository.getUserFromDb()).thenReturn(flowOf(KResult.success(
          UserLocal(1, "a", "09/10/1996", "a")
        )))
        testContextProvider = TestContextProvider()
        viewModel =
            EditUserProfileViewModel(
              userRepository = userRepository,
              dateToStringMapper = DateToStringMapper(),
              userUiToUserLocalMapper = UserUiToUserLocalMapper(),
              userLocalToUserUiMapper = UserLocalToUserUiMapper(),
              userNameValidator = initUserNameValidator(),
              birthDateValidator = initBirthDateValidator(),
              imageUriValidator = initImageUriValidator(),
              stringToDateMapper = StringToDateMapper(),
              contextProvider = testContextProvider
            )
    }

    private fun initUserNameValidator(): Validator<String> {
        val rules: Set<ValidationRule<String>> =
            setOf(TextMinLengthRule(4), TextWithoutDigitsRule())
        return Validator<String>().apply { addRules(rules) }
    }

    private fun initBirthDateValidator(): Validator<Date?> {
        val rules: Set<ValidationRule<Date?>> =
            setOf(ChosenDateBeforeCurrentDateRule(), DateIsNotNullRule())
        return Validator<Date?>().apply { addRules(rules) }
    }

    private fun initImageUriValidator(): Validator<Uri?> {
        val rules: Set<ValidationRule<Uri?>> = setOf(UriIsNotNullRule())
        return Validator<Uri?>().apply { addRules(rules) }
    }

    @Test
    fun userNameWasChanged() {
        val userName = "John Doe"
        viewModel.setUserName(userName)
        assertThat(viewModel.userState.value.name).isEqualTo(userName)
    }

    @Test
    fun birthDateWasChanged() {
        val userBirthDate = "01/01/2001"
        viewModel.setUserBirthDate(userBirthDate)
        assertThat(viewModel.userState.value.birthDate).isEqualTo(userBirthDate)
    }

    @Test
    fun userProfileImageUriWasChanged() {
        viewModel.setProfileImageUri(uri)
        assertThat(viewModel.userState.value.profileImageUri).isEqualTo(uri)
    }

    @Test
    fun applyButtonDisabledWhenUserNameIsInvalid() = runBlockingTest {
        val invalidUserName = "abc"
        val validUserBirthDate = "01/01/2011"
        viewModel.setUserName(invalidUserName)
        viewModel.setUserBirthDate(validUserBirthDate)
        viewModel.setProfileImageUri(uri)
        assertThat(viewModel.applyButton.first()).isFalse()
    }

    @Test
    fun applyButtonDisabledWhenBirthDateIsInvalid() = runBlockingTest {
        val validUserName = "John&Doe"
        val invalidUserBirthDate = "01/01/2071"
        viewModel.setUserName(validUserName)
        viewModel.setUserBirthDate(invalidUserBirthDate)
        viewModel.setProfileImageUri(uri)
        assertThat(viewModel.applyButton.first()).isFalse()
    }

    @Test
    fun applyButtonDisabledWhenImageUriIsInvalid() = runBlockingTest {
        val validUserName = "John Doe?"
        val validUserBirthDate = "01/01/2021"
        val invalidUri = Uri.EMPTY
        viewModel.setUserName(validUserName)
        viewModel.setUserBirthDate(validUserBirthDate)
        viewModel.setProfileImageUri(invalidUri)
        assertThat(viewModel.applyButton.first()).isFalse()
    }

    @Test
    fun applyButtonEnabledWhenValidationIsCorrect() = runBlockingTest {
        val validUserName = "John_Doe!"
        val validUserBirthDate = "12/08/2021"
        viewModel.setUserName(validUserName)
        viewModel.setUserBirthDate(validUserBirthDate)
        viewModel.setProfileImageUri(uri)
        assertThat(viewModel.applyButton.first()).isTrue()
    }

    @Test
    fun userNameErrorEmitsTrueIfNameIsValid() {
        val validUserName = "John+Doe"
        viewModel.setUserName(validUserName)
        assertThat(viewModel.userNameError.value).isTrue()
    }

    @Test
    fun userNameErrorEmitsFalseIfNameIsInvalid() {
        val invalidUserName = "123"
        viewModel.setUserName(invalidUserName)
        assertThat(viewModel.userNameError.value).isFalse()
    }

    @Test
    fun userBirthDateErrorEmitsTrueIfBirthDateIsValid() {
        val validUserBirthDate = "01/01/2001"
        viewModel.setUserBirthDate(validUserBirthDate)
        assertThat(viewModel.userBirthDateError.value).isTrue()
    }

    @Test
    fun userBirthDateErrorEmitsFalseIfBirthDateIsInvalid() {
        val invalidUserBirthDate = "01/01/2051"
        viewModel.setUserBirthDate(invalidUserBirthDate)
        assertThat(viewModel.userBirthDateError.value).isFalse()
    }

    @Test
    fun userImageUriErrorEmitsTrueIfImageUriIsValid() {
        viewModel.setProfileImageUri(uri)
        assertThat(viewModel.userImageUriError.value).isTrue()
    }

    @Test
    fun userImageUriErrorEmitsFalseIfImageUriIsInvalid() {
        val invalidUri = Uri.EMPTY
        viewModel.setProfileImageUri(invalidUri)
        assertThat(viewModel.userImageUriError.value).isFalse()
    }

    @Test
    fun validDateWasSet() {
        val validDay = 1
        val validMonth = 0
        val validYear = 2001
        val config = DatePickerConfig(validDay, validMonth, validYear)
        val date = "01/01/2001"
        viewModel.setUserDate(config)
        assertThat(viewModel.userState.value.birthDate).isEqualTo(date)
    }

    @Test
    fun invalidDateWasNotSet() {
        val invalidDay = 1
        val invalidMonth = 0
        val invalidYear = 2041
        val config = DatePickerConfig(invalidDay, invalidMonth, invalidYear)
        val date = "01/01/2041"
        viewModel.setUserDate(config)
        assertThat(viewModel.userState.value.birthDate).isNotEqualTo(date)
    }

    @Test
    fun toastSharedFlowEmitsToastMessageWhenDateIsInvalid() = runBlockingTest {
        launch {
            val invalidDay = 1
            val invalidMonth = 0
            val invalidYear = 2052
            val invalidConfig = DatePickerConfig(invalidDay, invalidMonth, invalidYear)
            viewModel.setUserDate(invalidConfig)
            assertThat(viewModel.toast.first()).isEqualTo(NOT_VALID_DATE)
        }.cancel()
    }

    //    TODO Exception in thread "main @coroutine#5" java.lang.NullPointerException
    @Test
    fun activityWasFinishedWhenInsertionWasSuccessful() {
        testCoroutineRule.runBlockingTest {
            val fakeInsertionResult: Long = 1
            whenever(userRepository.insertUserToDb(anyOrNull())).thenReturn(
              flowOf((KResult.success(fakeInsertionResult)))
            )

            val navValue = async {
                viewModel.navigation.first()
            }

            viewModel.insertUser()


            assertThat(navValue.await()).isEqualTo(FinishActivityNavigation)
        }
    }

    //TODO    Exception in thread "main @coroutine#5" java.lang.NullPointerException
    @Test
    fun activityWasNotFinishedWhenInsertionWasNotSuccessful() = runBlockingTest {
                val fakeInt = 1
                val fakeString = "a"
                val fakeUserLocal = UserLocal(fakeInt, fakeString, fakeString, fakeString)
                val error = NullPointerException()
                whenever(userRepository.insertUserToDb(fakeUserLocal)).thenReturn(
                  flowOf((KResult.failure(error)))
                )
                viewModel.insertUser()
                assertThat(viewModel.navigation.first()).isNotEqualTo(FinishActivityNavigation)
    }
}
