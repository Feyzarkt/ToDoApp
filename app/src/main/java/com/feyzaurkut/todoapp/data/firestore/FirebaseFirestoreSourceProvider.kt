package com.feyzaurkut.todoapp.data.firestore

import com.feyzaurkut.todoapp.data.model.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

const val USERS = "users"
const val NOTES = "notes"

class FirebaseFirestoreSourceProvider @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) :
    FirebaseFirestoreSource {

    override suspend fun createNote(note: Note) {
        auth.uid?.let {
            firebaseFirestore.collection(USERS).document(it).collection(NOTES).add(note).await()
        }
    }

}