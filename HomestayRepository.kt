package com.example.nammahomestay.data

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class HomestayRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
) {

    private fun homestayIdFromAuth(): String {
        return auth.currentUser?.uid
            ?: error("Host user is not signed in")
    }

    suspend fun saveProfile(profile: HomestayProfile): Result<Unit> {
        return runCatching {
            val id = if (profile.id.isBlank()) homestayIdFromAuth() else profile.id
            db.collection("homestays")
                .document(id)
                .set(
                    profile.copy(id = id),
                    SetOptions.merge()
                )
                .await()
        }
    }

    suspend fun loadProfile(): Result<HomestayProfile?> {
        return runCatching {
            val id = homestayIdFromAuth()
            val snap = db.collection("homestays").document(id).get().await()
            snap.toObject(HomestayProfile::class.java)
                ?.copy(id = snap.id)
        }
    }

    suspend fun uploadImages(
        uris: List<Uri>,
        homestayId: String = homestayIdFromAuth()
    ): Result<List<String>> {
        if (uris.isEmpty()) return Result.success(emptyList())

        val tasks = uris.map { uri ->
            val filename = uri.lastPathSegment ?: "image_${System.currentTimeMillis()}.jpg"
            val storageRef = storage.reference
                .child("homestays/$homestayId/images/${System.currentTimeMillis()}_$filename")

            storageRef.putFile(uri).await()
            storageRef.downloadUrl.await().toString()
        }

        return runCatching {
            tasks
        }
    }

    suspend fun saveMenu(menu: String, homestayId: String = homestayIdFromAuth()): Result<Unit> {
        return runCatching {
            db.collection("homestays")
                .document(homestayId)
                .set(mapOf("menu" to menu), SetOptions.merge())
                .await()
        }
    }

    suspend fun loadMenu(homestayId: String = homestayIdFromAuth()): Result<String?> {
        return runCatching {
            val snap = db.collection("homestays").document(homestayId).get().await()
            snap.getString("menu")
        }
    }

    suspend fun appendImageUrls(
        imageUrls: List<String>,
        homestayId: String = homestayIdFromAuth()
    ): Result<Unit> {
        if (imageUrls.isEmpty()) return Result.success(Unit)
        return runCatching {
            db.collection("homestays")
                .document(homestayId)
                .update("images", imageUrls)
                .await()
        }
    }
}

data class HomestayProfile(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val menu: String = "",
    val images: List<String> = emptyList()
)


