package com.feyzaurkut.todoapp.data.firestore

import com.feyzaurkut.todoapp.data.model.Note


interface FirebaseFirestoreSource {

    suspend fun createNote(note: Note)

}