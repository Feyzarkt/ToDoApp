package com.feyzaurkut.todoapp.presentation.home.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.feyzaurkut.todoapp.data.model.Note
import com.feyzaurkut.todoapp.databinding.CreateNoteDialogBinding
import com.feyzaurkut.todoapp.presentation.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateNoteDialog : DialogFragment() {

    private lateinit var binding: CreateNoteDialogBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CreateNoteDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onResume() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(900, LinearLayout.LayoutParams.WRAP_CONTENT)
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDialog()
    }

    private fun initDialog() {
        with(binding) {
            btnCreate.setOnClickListener {
                val title = etTitle.text.toString()
                val description = etDescription.text.toString()
                if (title.isEmpty() || description.isEmpty()) {
                    Toast.makeText(context, "Please check the fields", Toast.LENGTH_SHORT).show()
                } else {
                    createNote(title, description)
                }
            }
        }
    }

    private fun createNote(title: String, description: String) {
        homeViewModel.createNote(Note(null, title, description))
        Toast.makeText(context, "Please swipe to refresh the page..", Toast.LENGTH_SHORT).show()
        dismiss()
    }

}