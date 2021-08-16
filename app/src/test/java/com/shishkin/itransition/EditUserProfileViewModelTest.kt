package com.shishkin.itransition

import android.net.Uri
import com.google.common.truth.Truth.assertThat
import com.shishkin.itransition.base.BaseTest
import com.shishkin.itransition.db.UserLocal
import com.shishkin.itransition.gui.edituserprofile.DatePickerConfig
import com.shishkin.itransition.gui.edituserprofile.EditUserProfileViewModel
import com.shishkin.itransition.gui.edituserprofile.mappers.DateToStringMapper
import com.shishkin.itransition.gui.edituserprofile.mappers.StringToDateMapper
import com.shishkin.itransition.gui.userprofile.mappers.UserLocalToUserUiMapper
import com.shishkin.itransition.gui.userprofile.mappers.UserUiToUserLocalMapper
import com.shishkin.itransition.navigation.FinishActivityNavigation
import com.shishkin.itransition.network.entities.KResult
import com.shishkin.itransition.repository.UserRepository
import com.shishkin.itransition.validators.Validator
import com.shishkin.itransition.validators.rules.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.whenever
import java.util.*

const val FAKE_INT = 1
const val FAKE_STRING = "a"
const val FAKE_DATE = "01/01/2001"

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
        whenever(userRepository.getUserFromDb()).thenReturn(
            flowOf(KResult.success(UserLocal(FAKE_INT, FAKE_STRING, FAKE_DATE, FAKE_STRING)))
        )
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
        val rules: Set<Rule<String>> =
            setOf(TextMinLengthRule(4), TextWithoutDigitsRule())
        return Validator<String>().apply { addRules(rules) }
    }

    private fun initBirthDateValidator(): Validator<Date?> {
        val rules: Set<Rule<Date?>> =
            setOf(ChosenDateBeforeCurrentDateRule(), DateIsNotNullRule())
        return Validator<Date?>().apply { addRules(rules) }
    }

    private fun initImageUriValidator(): Validator<Uri?> {
        val rules: Set<Rule<Uri?>> = setOf(UriIsNotNullRule())
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
    fun applyButtonDisabledWhenUserNameIsInvalid() {
        testCoroutineRule.runBlockingTest {
            val invalidUserName = "abc"
            val validUserBirthDate = "01/01/2011"
            viewModel.setUserName(invalidUserName)
            viewModel.setUserBirthDate(validUserBirthDate)
            viewModel.setProfileImageUri(uri)
            assertThat(viewModel.applyButton.first()).isFalse()
        }
    }

    @Test
    fun applyButtonDisabledWhenBirthDateIsInvalid() {
        testCoroutineRule.runBlockingTest {
            val validUserName = "John&Doe"
            val invalidUserBirthDate = "01/01/2071"
            viewModel.setUserName(validUserName)
            viewModel.setUserBirthDate(invalidUserBirthDate)
            viewModel.setProfileImageUri(uri)
            assertThat(viewModel.applyButton.first()).isFalse()
        }
    }

    @Test
    fun applyButtonDisabledWhenImageUriIsInvalid() {
        testCoroutineRule.runBlockingTest {
            val validUserName = "John Doe?"
            val validUserBirthDate = "01/01/2021"
            val invalidUri = Uri.EMPTY
            viewModel.setUserName(validUserName)
            viewModel.setUserBirthDate(validUserBirthDate)
            viewModel.setProfileImageUri(invalidUri)
            assertThat(viewModel.applyButton.first()).isFalse()
        }
    }

    @Test
    fun applyButtonEnabledWhenValidationIsCorrect() {
        testCoroutineRule.runBlockingTest {
            val validUserName = "John_Doe!"
            val validUserBirthDate = "12/08/2021"
            viewModel.setUserName(validUserName)
            viewModel.setUserBirthDate(validUserBirthDate)
            viewModel.setProfileImageUri(uri)
            assertThat(viewModel.applyButton.first()).isTrue()
        }
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
    fun toastSharedFlowEmitsToastMessageWhenDateIsInvalid() {
        testCoroutineRule.runBlockingTest {
            val invalidDay = 1
            val invalidMonth = 0
            val invalidYear = 2052
            val invalidConfig = DatePickerConfig(invalidDay, invalidMonth, invalidYear)
            val toastValue = async {
                viewModel.toast.first()
            }
            viewModel.setUserDate(invalidConfig)
            assertThat(toastValue.await()).isEqualTo(R.string.edit_user_profile_not_valid_date_toast_message)
        }
    }

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

    @Test
    fun toastSharedFlowEmitsToastMessageWhenInsertionFailed() {
        testCoroutineRule.runBlockingTest {
            val error = NullPointerException()
            whenever(userRepository.insertUserToDb(anyOrNull())).thenReturn(
                flowOf((KResult.failure(error)))
            )
            val toastValue = async {
                viewModel.toast.first()
            }
            viewModel.insertUser()
            assertThat(toastValue.await()).isEqualTo(R.string.edit_user_profile_insertion_failed_toast_message)
        }
    }
}
