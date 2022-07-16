package com.feyzaurkut.todoapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feyzaurkut.todoapp.data.model.Note
import com.feyzaurkut.todoapp.data.model.RequestState
import com.feyzaurkut.todoapp.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val createNoteUseCase: CreateNoteUseCase,
    private val getNotesUseCase: GetNotesUseCase,
    private val getSelectedNoteUseCase: GetSelectedNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    private val _notesState = MutableStateFlow<RequestState<ArrayList<Note>>?>(null)
    val notesState: StateFlow<RequestState<ArrayList<Note>>?> = _notesState

    private val _selectedNoteState = MutableStateFlow<RequestState<Note>?>(null)
    val selectedNoteState: StateFlow<RequestState<Note>?> = _selectedNoteState

    fun createNote(note: Note) = viewModelScope.launch {
        createNoteUseCase.invoke(note).collect {
        }
    }

    fun getNotes() = viewModelScope.launch {
        getNotesUseCase.invoke().collect {
            _notesState.value = it
        }
    }

    fun getSelectedNote(docId: String) = viewModelScope.launch {
        getSelectedNoteUseCase.invoke(docId).collect {
            _selectedNoteState.value = it
        }
    }

    fun updateNote(docId: String, title: String, description: String) = viewModelScope.launch {
        updateNoteUseCase.invoke(docId, title, description).collect {
        }
    }

    fun deleteNote(docId: String) = viewModelScope.launch {
        deleteNoteUseCase.invoke(docId).collect {
        }
    }
}