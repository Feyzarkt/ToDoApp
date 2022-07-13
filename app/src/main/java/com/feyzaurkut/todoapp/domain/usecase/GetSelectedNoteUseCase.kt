package com.feyzaurkut.todoapp.domain.usecase

import com.feyzaurkut.todoapp.data.model.RequestState
import com.feyzaurkut.todoapp.data.repository.FirebaseRepository
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetSelectedNoteUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {

    suspend fun invoke(docId: String) = flow {
        try {
            emit(RequestState.Loading())
            emit(RequestState.Success(firebaseRepository.getSelectedNote(docId)))
        } catch (e: Exception) {
            emit(RequestState.Error(e))
        }
    }
}