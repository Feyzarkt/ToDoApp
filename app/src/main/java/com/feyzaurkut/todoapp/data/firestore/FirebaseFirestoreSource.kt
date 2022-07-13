package com.feyzaurkut.todoapp.data.firestore

import com.feyzaurkut.todoapp.data.model.Note


interface FirebaseFirestoreSource {

    suspend fun createNote(note: Note)
    suspend fun getNotes(): ArrayList<Note>
    suspend fun getSelectedNote(docId: String): Note
    suspend fun updateNote(docId: String, title: String, description: String)

}