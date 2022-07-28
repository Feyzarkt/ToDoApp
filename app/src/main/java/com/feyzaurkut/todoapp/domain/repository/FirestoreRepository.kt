package com.feyzaurkut.todoapp.domain.repository

import com.feyzaurkut.todoapp.data.model.Note


interface FirestoreRepository {

    suspend fun createNote(note: Note)
    suspend fun getNotes(): ArrayList<Note>
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(docId: String)

}