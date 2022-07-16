package com.feyzaurkut.todoapp.domain.usecase

import com.feyzaurkut.todoapp.domain.repository.FirestoreRepository
import com.feyzaurkut.todoapp.data.model.Note
import com.feyzaurkut.todoapp.data.model.RequestState
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class CreateNoteUseCase @Inject constructor(private val repository: FirestoreRepository) {

    suspend fun invoke(note: Note) = flow {
        try {
            emit(RequestState.Loading())
            emit(RequestState.Success(repository.createNote(note)))
        } catch (e: Exception) {
            emit(RequestState.Error(e))
        }
    }
}