package com.feyzaurkut.todoapp.presentation.home

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.feyzaurkut.todoapp.R
import com.feyzaurkut.todoapp.data.model.Note
import com.feyzaurkut.todoapp.data.model.RequestState
import com.feyzaurkut.todoapp.databinding.FragmentHomeBinding
import com.feyzaurkut.todoapp.utils.OnClickListener
import com.feyzaurkut.todoapp.utils.SharedPreferences
import com.feyzaurkut.todoapp.utils.SwipeGesture
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

        binding.tvWelcome.text =
            "Welcome ${SharedPreferences(requireContext()).getUsernameString()}!"
        initListeners()
        getNotes()
        onBackPressed()

        return binding.root
    }

    private fun initListeners() {
        with(binding) {
            ivLogout.setOnClickListener {
                initLogoutDialog()
            }
            btnCreateItem.setOnClickListener {
                showCreateDialog()
            }
            swipeRefreshLayout.setOnRefreshListener {
                getNotes()
            }
        }
    }

    private fun initLogoutDialog() {
        val alertDialogBuilder = AlertDialog.Builder(activity)
        alertDialogBuilder.setTitle("Logout")
        alertDialogBuilder.setMessage("Are you sure you want to exit?")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            auth.signOut()
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
        alertDialogBuilder.setNegativeButton("Cancel") { _, _ -> }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun getNotes() {
        homeViewModel.getNotes()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.notesState.collect { notesResult ->
                    when (notesResult) {
                        is RequestState.Success -> {
                            binding.progressBar.isVisible = false
                            Log.e("Success", notesResult.data.toString())
                            initRecycler(notesResult.data)
                            binding.swipeRefreshLayout.isRefreshing = false
                        }
                        is RequestState.Error -> {
                            binding.progressBar.isVisible = false
                            Log.e("Error", notesResult.exception.toString())
                        }
                        is RequestState.Loading -> {
                            binding.progressBar.isVisible = true
                            Log.e("Loading", "Loading")
                        }
                    }
                }
            }
        }
    }

    private fun initRecycler(notesList: ArrayList<Note>) {

        val toDoAdapter = ToDoListRecyclerAdapter(
            requireContext(),
            requireActivity(),
            notesList,
            object : OnClickListener {
                override fun onClick(position: Int) {
                    showUpdateDialog(notesList[position])
                }
            })

        val swipeGesture = object : SwipeGesture(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        notesList[viewHolder.adapterPosition].id?.let { homeViewModel.deleteNote(it) }
                        getNotes()
                    }
                }
            }
        }

        ItemTouchHelper(swipeGesture).attachToRecyclerView(binding.rvToDoList)
        binding.rvToDoList.adapter = toDoAdapter
    }

    private fun showCreateDialog() {
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.create_note_dialog, null)
        val mBuilder = AlertDialog.Builder(context).setView(mDialogView).show()

        val createButton = mDialogView.findViewById<AppCompatImageView>(R.id.btnCreate)
        val titleEditText = mDialogView.findViewById<AppCompatEditText>(R.id.etTitle)
        val descriptionEditText = mDialogView.findViewById<AppCompatEditText>(R.id.etDescription)

        createButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(context, "Please check the fields", Toast.LENGTH_SHORT).show()
            } else {
                homeViewModel.createNote(Note(null, title, description))
                getNotes()
                mBuilder.dismiss()
            }
        }
    }

    private fun showUpdateDialog(note: Note) {
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.update_note_dialog, null)
        val mBuilder = AlertDialog.Builder(context).setView(mDialogView).show()

        val createButton = mDialogView.findViewById<AppCompatImageView>(R.id.btnUpdate)
        val titleEditText = mDialogView.findViewById<AppCompatEditText>(R.id.etTitle)
        val descriptionEditText = mDialogView.findViewById<AppCompatEditText>(R.id.etDescription)

        titleEditText.setText(
            note.title.toString(),
            TextView.BufferType.EDITABLE
        )
        descriptionEditText.setText(
            note.description.toString(),
            TextView.BufferType.EDITABLE
        )
        createButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(context, "Please check the fields", Toast.LENGTH_SHORT).show()
            } else {
                val updatedNote = Note(note.id, title, description)
                homeViewModel.updateNote(updatedNote)
                getNotes()
                mBuilder.dismiss()
            }
        }
    }

    private fun onBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

}