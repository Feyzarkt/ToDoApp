package com.feyzaurkut.todoapp.ui.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.feyzaurkut.todoapp.R
import com.feyzaurkut.todoapp.data.model.Note
import com.feyzaurkut.todoapp.data.model.RequestState
import dev.sasikanth.colorsheet.ColorSheet
import com.feyzaurkut.todoapp.databinding.CreateNoteDialogBinding
import com.feyzaurkut.todoapp.databinding.UpdateNoteDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class UpdateNoteDialog(private val note: Note) : DialogFragment() {

    private lateinit var binding: UpdateNoteDialogBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UpdateNoteDialogBinding.inflate(inflater)

        getNote()

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
           btnUpdate.setOnClickListener {
               val title = etTitle.text.toString()
               val description = etDescription.text.toString()
               updateNote(title, description)
               dismiss()
           }
       }
    }

    private fun updateNote(title: String, description: String) {
        if (title.isEmpty() || description.isEmpty()){
            Toast.makeText(context, "Please check the fields", Toast.LENGTH_SHORT).show()
        } else {
            Log.e("title", title)
            Log.e("descript", description)

            note.id?.let { homeViewModel.updateNote(it, title, description) }
        }
    }

    private fun getNote() {
        note.id?.let { homeViewModel.getSelectedNote(it) }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.selectedNoteState.collect { noteResult ->
                    when (noteResult) {
                        is RequestState.Success -> {
                            Log.e("Success", noteResult.data.toString())
                            binding.etTitle.setText(noteResult.data.title.toString(),TextView.BufferType.EDITABLE)
                            binding.etDescription.setText(noteResult.data.description.toString(),TextView.BufferType.EDITABLE)
                        }
                        is RequestState.Error -> {
                            Log.e("Error", noteResult.exception.toString())
                        }
                        is RequestState.Loading -> {
                            Log.e("Loading", "Loading")
                        }
                    }
                }
            }
        }
    }

}