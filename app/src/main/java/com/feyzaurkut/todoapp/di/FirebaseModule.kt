package com.feyzaurkut.todoapp.di

import com.feyzaurkut.todoapp.domain.repository.FirestoreRepository
import com.feyzaurkut.todoapp.data.repository.FirestoreRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Singleton
    @Provides
    fun provideFirebaseFirestoreInstance(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Singleton
    @Provides
    fun provideFirestoreRepository(
        firebaseFirestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): FirestoreRepository = FirestoreRepositoryImpl(firebaseFirestore, auth)

    @Singleton
    @Provides
    fun provideFirebaseAuthInstance(): FirebaseAuth {
        return Firebase.auth
    }
}