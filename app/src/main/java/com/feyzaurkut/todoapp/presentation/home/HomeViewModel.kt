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
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    private val _notesState = MutableStateFlow<RequestState<ArrayList<Note>>?>(null)
    val notesState: StateFlow<RequestState<ArrayList<Note>>?> = _notesState

    fun createNote(note: Note) = viewModelScope.launch {
        createNoteUseCase.invoke(note).collect {
        }
    }

    fun getNotes() = viewModelScope.launch {
        getNotesUseCase.invoke().collect {
            _notesState.value = it
        }
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        updateNoteUseCase.invoke(note).collect {
        }
    }

    fun deleteNote(docId: String) = viewModelScope.launch {
        deleteNoteUseCase.invoke(docId).collect {
        }
    }
}