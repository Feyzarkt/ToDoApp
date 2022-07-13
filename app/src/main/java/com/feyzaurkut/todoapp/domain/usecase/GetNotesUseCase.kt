package com.feyzaurkut.todoapp.domain.usecase

import com.feyzaurkut.todoapp.data.model.RequestState
import com.feyzaurkut.todoapp.data.repository.FirebaseRepository
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {

    suspend fun invoke() = flow {
        try {
            emit(RequestState.Loading())
            emit(RequestState.Success(firebaseRepository.getNotes()))
        } catch (e: Exception) {
            emit(RequestState.Error(e))
        }
    }
}