package com.feyzaurkut.todoapp.domain.usecase

import com.feyzaurkut.todoapp.data.model.Note
import com.feyzaurkut.todoapp.data.model.RequestState
import com.feyzaurkut.todoapp.domain.repository.FirestoreRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(private val repository: FirestoreRepository) {

    suspend fun invoke(note: Note) = flow {
        try {
            emit(RequestState.Loading())
            emit(RequestState.Success(repository.updateNote(note)))
        } catch (e: Exception) {
            emit(RequestState.Error(e))
        }
    }
}