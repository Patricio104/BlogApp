package com.patricio.blogapp.ui.auth

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.patricio.blogapp.R
import com.patricio.blogapp.core.Result
import com.patricio.blogapp.data.remote.auth.AuthDataSource
import com.patricio.blogapp.databinding.FragmentRegisterBinding
import com.patricio.blogapp.domain.auth.AuthRepoImpl
import com.patricio.blogapp.presentation.auth.AuthViewModel
import com.patricio.blogapp.presentation.auth.AuthViewModelFactory
import kotlin.math.sign

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<AuthViewModel> { AuthViewModelFactory(
        AuthRepoImpl(
        AuthDataSource()
    )
    ) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        signUp()
    }

    private fun signUp() {
        binding.btnSigup.setOnClickListener {
            val userName = binding.editTextUsername.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val confirmPassword = binding.editTextConfirmPassword.text.toString().trim()

            if (validateUserData(password, confirmPassword, email, userName)) return@setOnClickListener
            
            createUser(email, password, userName)
        }
    }

    private fun createUser(email: String, password: String, userName: String) {
        viewModel.signUp(email, password, userName).observe(viewLifecycleOwner, Observer { result->
            when(result){
                is Result.Loading->{
                    binding.progressBar.visibility=View.VISIBLE
                    binding.btnSigup.isEnabled=false
                }
                is Result.Success->{
                    binding.progressBar.visibility=View.GONE
                    findNavController().navigate(R.id.action_registerFragment_to_homeScreenFragment)
                }
                is Result.Failure->{
                    binding.progressBar.visibility=View.GONE
                    binding.btnSigup.isEnabled=true
                    Toast.makeText(requireContext(), "Error: ${result.exception}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun validateUserData(
            password: String,
            confirmPassword: String,
            email: String,
            userName: String
    ): Boolean {
        if (password != confirmPassword) {
            binding.editTextConfirmPassword.setError("Password does not match")
            binding.editTextPassword.setError("password does not match")
            return true
        }

        if (password.isEmpty()) {
            binding.editTextPassword.error = "Password is empty"
            return true
        }
        if (confirmPassword.isEmpty()) {
            binding.editTextConfirmPassword.error = "Confirm password is empty"
            return true
        }
        if (email.isEmpty()) {
            binding.editTextEmail.error = "Email is empty"
            return true
        }
        if (userName.isEmpty()) {
            binding.editTextUsername.error = "Username is empty"
            return true
        }
        return false
    }
}