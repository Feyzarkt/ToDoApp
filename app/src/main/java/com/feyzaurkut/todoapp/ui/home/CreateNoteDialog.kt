package com.feyzaurkut.todoapp.ui.home

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
                createNote(title, description)
                dismiss()
            }
        }
    }

    private fun createNote(title: String, description: String) {
        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(context, "Please check the fields", Toast.LENGTH_SHORT).show()
        } else {
            homeViewModel.createNote(Note(null, title, description))
        }
    }

}