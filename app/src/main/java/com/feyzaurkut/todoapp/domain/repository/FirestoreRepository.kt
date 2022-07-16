package com.feyzaurkut.todoapp.domain.repository

import com.feyzaurkut.todoapp.data.model.Note


interface FirestoreRepository {

    suspend fun createNote(note: Note)
    suspend fun getNotes(): ArrayList<Note>
    suspend fun getSelectedNote(docId: String): Note
    suspend fun updateNote(docId: String, title: String, description: String)
    suspend fun deleteNote(docId: String)

}