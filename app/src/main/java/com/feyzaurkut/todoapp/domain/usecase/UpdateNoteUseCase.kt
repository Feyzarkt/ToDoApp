package com.feyzaurkut.todoapp.domain.usecase

import com.feyzaurkut.todoapp.domain.repository.FirestoreRepository
import com.feyzaurkut.todoapp.data.model.RequestState
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(private val repository: FirestoreRepository) {

    suspend fun invoke(docId: String, title: String, description: String) = flow {
        try {
            emit(RequestState.Loading())
            emit(RequestState.Success(repository.updateNote(docId,title, description)))
        } catch (e: Exception) {
            emit(RequestState.Error(e))
        }
    }
}