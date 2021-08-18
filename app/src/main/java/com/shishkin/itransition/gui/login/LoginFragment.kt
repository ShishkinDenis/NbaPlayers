package com.shishkin.itransition.gui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.shishkin.itransition.R
import com.shishkin.itransition.databinding.FragmentLoginBinding
import com.shishkin.itransition.extensions.showLongToast
import com.shishkin.itransition.navigation.BaseNavigationEmitter
import com.shishkin.itransition.navigation.NavigationEmitter
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: LoginViewModelFactory
    private val viewModel: LoginViewModel by viewModels { viewModelFactory }

    private lateinit var _binding: FragmentLoginBinding
    private val binding get() = _binding

    private val navigationEmitter: NavigationEmitter by lazy {
        BaseNavigationEmitter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            viewModel.login()
        }
        initEditTextListeners()
        collectData()
        enableLoginButtonIfValid()
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.toast.collect { toastMessage ->
                        context?.showLongToast(toastMessage)
                    }
                }

                launch {
                    viewModel.navigation.collect { navigation ->
                        navigationEmitter.navigateTo(navigation)
                    }
                }

                launch {
                    viewModel.userNameError.collect { nameState ->
                        showErrorInUserProfileNameField(nameState)
                    }
                }

                launch {
                    viewModel.userPasswordError.collect { passwordState ->
                        showErrorInUserPasswordField(passwordState)
                    }
                }
            }
        }
    }

    private fun showErrorInUserProfileNameField(nameIsValid: Boolean) {
        if (nameIsValid) {
            binding.tilLoginName.error = null
        } else {
            binding.tilLoginName.error = getString(R.string.login_name_error)
        }
    }

    private fun showErrorInUserPasswordField(passwordIsValid: Boolean) {
        if (passwordIsValid) {
            binding.tilLoginPassword.error = null
        } else {
            binding.tilLoginPassword.error = getString(R.string.login_password_error)
        }
    }

    private fun enableLoginButtonIfValid() {
        binding.btnLogin.isEnabled = true
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginButton.collect { value ->
                    binding.btnLogin.isEnabled = value
                }
            }
        }
    }

    private fun initEditTextListeners() {
        binding.etLoginName.addTextChangedListener {
            viewModel.setUserName(it.toString())
        }
        binding.etLoginPassword.addTextChangedListener {
            viewModel.setUserPassword(it.toString())
        }
    }
}

