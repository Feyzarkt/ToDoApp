package com.feyzaurkut.todoapp.data.repository

import com.feyzaurkut.todoapp.data.firestore.FirebaseFirestoreSourceProvider
import com.feyzaurkut.todoapp.data.model.Note
import javax.inject.Inject


class FirebaseRepository @Inject constructor(
    private val firebaseFirestoreSourceProvider: FirebaseFirestoreSourceProvider
) {

    suspend fun createNote(note: Note) =
        firebaseFirestoreSourceProvider.createNote(note)

    suspend fun getNotes(): ArrayList<Note> =
        firebaseFirestoreSourceProvider.getNotes()

    suspend fun getSelectedNote(docId: String): Note =
        firebaseFirestoreSourceProvider.getSelectedNote(docId)

    suspend fun updateNote(docId: String, title: String, description: String) =
        firebaseFirestoreSourceProvider.updateNote(docId, title, description)

}