package com.feyzaurkut.todoapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.feyzaurkut.todoapp.R
import com.feyzaurkut.todoapp.data.model.Note
import com.feyzaurkut.todoapp.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    @Inject
    lateinit var auth: FirebaseAuth
    //private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)

        initListeners()

        return binding.root
    }

    private fun initListeners() {
        with(binding) {
            ivLogout.setOnClickListener {
                auth.signOut()
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            }
            btnCreateItem.setOnClickListener {
                createNote()
            }
        }
    }

    private fun createNote() {
        //homeViewModel.createNote(Note("Deneme Feyza2", "DENEME FEYZA 222"))
    }

}