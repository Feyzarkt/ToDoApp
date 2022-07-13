package com.feyzaurkut.todoapp.ui.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.feyzaurkut.todoapp.R
import com.feyzaurkut.todoapp.data.model.Note
import com.feyzaurkut.todoapp.data.model.RequestState
import com.feyzaurkut.todoapp.databinding.FragmentHomeBinding
import com.feyzaurkut.todoapp.utils.OnClickListener
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    @Inject
    lateinit var auth: FirebaseAuth
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)

        initListeners()
        getNotes()

        return binding.root
    }

    private fun initListeners() {
        with(binding) {
            ivLogout.setOnClickListener {
                auth.signOut()
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            }
            btnCreateItem.setOnClickListener {
                activity?.supportFragmentManager?.let {
                    CreateNoteDialog().show( it, "CreateNoteDialog")
                }
            }
            swipeRefreshLayout.setOnRefreshListener {
                getNotes()
            }
        }
    }

    private fun getNotes() {
        homeViewModel.getNotes()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.notesState.collect { notesResult ->
                    when (notesResult) {
                        is RequestState.Success -> {
                            Log.e("Success", notesResult.data.toString())
                            initRecycler(notesResult.data)
                            binding.swipeRefreshLayout.isRefreshing = false
                        }
                        is RequestState.Error -> {
                            Log.e("Error", notesResult.exception.toString())
                        }
                        is RequestState.Loading -> {
                            Log.e("Loading", "Loading")
                        }
                    }
                }
            }
        }
    }

    private fun initRecycler(notesList: ArrayList<Note>) {
        binding.rvToDoList.apply {
            adapter = ToDoListRecyclerAdapter(context, requireActivity(), notesList, object: OnClickListener{
                override fun onClick(position: Int) {
                    activity?.supportFragmentManager?.let {
                        UpdateNoteDialog(notesList[position]).show( it, "UpdateNoteDialog")
                    }
                }
            })
        }
    }

}