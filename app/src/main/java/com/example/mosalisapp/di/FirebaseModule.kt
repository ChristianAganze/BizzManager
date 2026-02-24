package com.example.mosalisapp.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.persistentCacheSettings
import org.koin.dsl.module

val firebaseModule = module {
    single { FirebaseAuth.getInstance() }
    single {
        val firestore = Firebase.firestore
        val settings = firestoreSettings {
            setLocalCacheSettings(persistentCacheSettings {})
        }
        firestore.firestoreSettings = settings
        firestore
    }
}
