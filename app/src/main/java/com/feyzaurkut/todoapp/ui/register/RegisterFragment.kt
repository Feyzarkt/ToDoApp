package com.feyzaurkut.todoapp.ui.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.feyzaurkut.todoapp.R
import com.feyzaurkut.todoapp.databinding.FragmentRegisterBinding
import com.feyzaurkut.todoapp.utils.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)

        initListeners()

        return binding.root
    }

    private fun initListeners() {

        with(binding) {
            btnSignUp.setOnClickListener {
                createNewAccount()
            }
            tvSignIn.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }

    private fun createNewAccount() {

        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val username = binding.etUsername.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Authentication success.", Toast.LENGTH_SHORT).show()
                    SharedPreferences(requireContext()).putUsernameString(username)
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                } else {
                    Log.e("RegisterFailed", task.exception.toString())
                    Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    binding.etUsername.setText("")
                    binding.etEmail.setText("")
                    binding.etPassword.setText("")
                }
            }
    }
}