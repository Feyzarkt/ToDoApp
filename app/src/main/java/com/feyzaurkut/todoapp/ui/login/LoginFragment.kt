package com.feyzaurkut.todoapp.ui.login

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.feyzaurkut.todoapp.R
import com.feyzaurkut.todoapp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)

        initListeners()
        binding.tvSignUp.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        return binding.root
    }

    private fun initListeners() {

        with(binding) {
            btnSignIn.setOnClickListener {
                signIn()
            }
            tvSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }

    private fun signIn() {

        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(context, "Please check related fields", Toast.LENGTH_SHORT).show()
        }
        else {
            binding.progressBar.isVisible = true
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Authentication success.", Toast.LENGTH_SHORT).show()
                        binding.progressBar.isVisible = false
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    } else {
                        Log.e("SignInFailed", task.exception.toString())
                        Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        binding.etEmail.setText("")
                        binding.etPassword.setText("")
                    }
                }
        }

    }

}