package com.feyzaurkut.todoapp.data.firestore

import com.feyzaurkut.todoapp.data.model.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CompletableDeferred
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

    override suspend fun getNotes(): ArrayList<Note> {
        val noteList: ArrayList<Note> = arrayListOf()
        val def = CompletableDeferred<ArrayList<Note>>()
        auth.uid?.let { uid ->
            firebaseFirestore.collection(USERS).document(uid).collection(NOTES)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) it.result.let { note ->
                        val notesDocuments = note.documents
                        notesDocuments.forEach { noteDocument ->
                            val note = Note(noteDocument.id, noteDocument.getString("title"), noteDocument.getString("description"))
                            noteList.add(note)
                        }
                        def.complete(noteList)
                    }
                }
        }
        return def.await()
    }

    override suspend fun getSelectedNote(docId: String): Note {
        val def = CompletableDeferred<Note>()
        auth.uid?.let { uid ->
            firebaseFirestore.collection(USERS).document(uid).collection(NOTES).document(docId)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) it.result.let { noteDocument ->
                        val note = Note(noteDocument.id, noteDocument.getString("title"), noteDocument.getString("description"))
                        def.complete(note)
                    }
                }
        }
        return def.await()
    }

    override suspend fun updateNote(docId: String, title: String, description: String) {
        auth.uid?.let {
            firebaseFirestore.collection(USERS).document(it).collection(NOTES).document(docId)
                .update(mapOf(
                "title" to title,
                "description" to description
            )).await()
        }
    }

    override suspend fun deleteNote(docId: String) {
        auth.uid?.let {
            firebaseFirestore.collection(USERS).document(it).collection(NOTES).document(docId)
                .delete().await()
        }
    }

}