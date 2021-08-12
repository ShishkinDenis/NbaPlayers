package com.shishkin.itransition

import android.net.Uri
import com.google.common.truth.Truth
import com.shishkin.itransition.gui.edituserprofile.EditUserProfileViewModel
import com.shishkin.itransition.gui.edituserprofile.mappers.DateToStringMapper
import com.shishkin.itransition.gui.edituserprofile.mappers.StringToDateMapper
import com.shishkin.itransition.gui.userprofile.mappers.UserLocalToUserUiMapper
import com.shishkin.itransition.gui.userprofile.mappers.UserUiToUserLocalMapper
import com.shishkin.itransition.repository.UserRepository
import com.shishkin.itransition.validators.Validator
import com.shishkin.itransition.validators.rules.Rule
import com.shishkin.itransition.validators.rules.TextMinLengthRule
import com.shishkin.itransition.validators.rules.TextWithoutDigitsRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class EditUserProfileViewModelTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var uri: Uri

    @Mock
    private lateinit var userNameValidator: Validator<String>

    private lateinit var viewModel: EditUserProfileViewModel

    private lateinit var testContextProvider: TestContextProvider

    @Before
    fun setUp() {
        testContextProvider = TestContextProvider()
        viewModel =
            EditUserProfileViewModel(
                userRepository,
                DateToStringMapper(),
                UserUiToUserLocalMapper(),
                UserLocalToUserUiMapper(),
                Validator(),
                Validator(),
                Validator(),
                StringToDateMapper(),
                testContextProvider
            )
    }

    @Test
    fun userNameWasChanged() {
        val userName = "John Doe"
        viewModel.setUserName(userName)
        Truth.assertThat(viewModel.userState.value.name).isEqualTo(userName)
    }

    @Test
    fun birthDateWasChanged() {
        val userBirthDate = "01/01/2001"
        viewModel.setUserBirthDate(userBirthDate)
        Truth.assertThat(viewModel.userState.value.birthDate).isEqualTo(userBirthDate)
    }

    @Test
    fun userProfileImageUriWasChanged() {
        viewModel.setProfileImageUri(uri)
        Truth.assertThat(viewModel.userState.value.profileImageUri).isEqualTo(uri)
    }

    @Test
    fun applyButtonDisabledWhenUserNameErrorDataIsFalse() = runBlockingTest {
        viewModel.userNameErrorData.value = false
        viewModel.userBirthDateErrorData.value = true
        viewModel.userImageUriErrorData.value = true
        launch {
            viewModel.applyButton.collect {
                Truth.assertThat(it).isFalse()
            }
        }.cancel()
    }

    @Test
    fun applyButtonDisabledWhenBirthDateErrorDataIsFalse() = runBlockingTest {
        viewModel.userNameErrorData.value = true
        viewModel.userBirthDateErrorData.value = false
        viewModel.userImageUriErrorData.value = true
        launch {
            viewModel.applyButton.collect {
                Truth.assertThat(it).isFalse()
            }
        }.cancel()
    }

    @Test
    fun applyButtonDisabledWhenImageUriErrorDataIsFalse() = runBlockingTest {
        viewModel.userNameErrorData.value = true
        viewModel.userBirthDateErrorData.value = true
        viewModel.userImageUriErrorData.value = false
        launch {
            viewModel.applyButton.collect {
                Truth.assertThat(it).isFalse()
            }
        }.cancel()
    }

    @Test
    fun applyButtonEnabledWhenValidationIsCorrect() = runBlockingTest {
        viewModel.userNameErrorData.value = true
        viewModel.userBirthDateErrorData.value = true
        viewModel.userImageUriErrorData.value = true
        launch {
            viewModel.applyButton.collect {
                Truth.assertThat(it).isTrue()
            }
        }.cancel()
    }

    @Test
    fun userNameErrorEmitsTrueIfNameIsValid() {
        val validUserName = "John Doe"
        viewModel.setUserName(validUserName)
        Truth.assertThat(viewModel.userNameError.value).isTrue()
    }

    //TODO не работает
    @Test
    fun userNameErrorEmitsFalseIfNameIsInvalid() = runBlockingTest {
        val invalidUserName = "123"
        viewModel.setUserName(invalidUserName)
        whenever(userNameValidator.validate(invalidUserName)).thenReturn(false)
        viewModel.setErrorIfInvalid()
        Truth.assertThat(viewModel.userNameError.value).isFalse()
    }

    // TODO здесь реальный валидатор,удалить
    @Test
    fun validatorReturnsFalseWhenNameIsInvalid() = runBlockingTest {
        val userName = "123"
        viewModel.setUserName(userName)
        val rules: Set<Rule<String>> =
            setOf(TextMinLengthRule(4), TextWithoutDigitsRule())
        val validator = Validator<String>().apply { addRules(rules) }
        Truth.assertThat(validator.validate(viewModel.userState.value.name)).isFalse()
    }
}