package com.saxomoose.frontend.ui.auth.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.saxomoose.frontend.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreate(savedInstanceState)
        binding = FragmentRegisterBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = binding.name
        val username = binding.username
        val password = binding.password
        val registerButton = binding.registerButton
        val loading = binding.registerLoading

        binding.viewModel = viewModel

        viewModel.registerFormState.observe(viewLifecycleOwner, Observer {
            val registerState = it ?: return@Observer

            // Disable login button unless name/ username / password are valid.
            registerButton.isEnabled = registerState.isDataValid

            if (registerState.nameError != null) {
                name.error = getString(registerState.nameError)
            }

            if (registerState.usernameError != null) {
                username.error = getString(registerState.usernameError)
            }
            if (registerState.passwordError != null) {
                password.error = getString(registerState.passwordError)
            }
        })

        viewModel.loginResult.observe(viewLifecycleOwner, Observer {
            val registerResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (!registerResult) {
                showRegisterFailed()
            }
            if (registerResult) {
                val action = RegisterFragmentDirections.actionFragmentRegisterToFragmentLogin(successfulRegistration = true)
                findNavController().navigate(action)
            }
        })

        name.afterTextChanged {
            viewModel.registerDataChanged(name.text.toString(), username.text.toString(), password.text.toString())
        }

        username.afterTextChanged {
            viewModel.registerDataChanged(name.text.toString(), username.text.toString(), password.text.toString())
        }

        password.apply {
            afterTextChanged {
                viewModel.registerDataChanged(name.text.toString(), username.text.toString(), password.text.toString())
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> viewModel.register(name.text.toString(), username.text.toString(), password.text.toString())
                }
                false
            }

            registerButton.setOnClickListener {
                loading.visibility = View.VISIBLE
                viewModel.register(name.text.toString(), username.text.toString(), password.text.toString())
            }
        }
    }

    private fun showRegisterFailed() {
        Toast.makeText(activity?.applicationContext, "Registration failed", Toast.LENGTH_LONG)
            .show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        }
    })
}