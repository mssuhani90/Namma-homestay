package com.example.nammahomestay.data

import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    fun getCurrentUid(): String? = auth.currentUser?.uid
    fun signOut() = auth.signOut()
}

