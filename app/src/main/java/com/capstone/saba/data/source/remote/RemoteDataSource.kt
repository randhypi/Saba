package com.capstone.saba.data.source.remote

import android.util.Log
import android.widget.Toast
import com.capstone.saba.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RemoteDataSource @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    companion object {
        val TAG = RemoteDataSource::class.java.simpleName
    }


    internal fun runAll() {
        signInWithEmail("randhypradanairsan@gmail.com", "12345678")
    }

    fun signUpWithEmail(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
    }

    fun signInWithEmail(email: String, password: String): Flowable<Boolean> {

        var result = PublishSubject.create<Boolean>()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
               result.onNext(task.isSuccessful)
            }

        return result.toFlowable(BackpressureStrategy.BUFFER)

    }

    fun getDataUser(): Flowable<User> {
        var user = PublishSubject.create<User>()
        val currentUser = auth.currentUser

        val userId = currentUser?.uid
        val docRef = db.collection("users").document(userId.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    val userData = document.toObject<User>()
                    Log.d("REMOTE DATA", userData.toString())
                    user.onNext(
                        User(
                            id = userData?.id.toString(),
                            email = userData?.email.toString(),
                            gender = userData?.gender.toString(),
                            name = userData?.name.toString(),
                            birthOfDate = userData?.birthOfDate.toString(),
                            urlAva = userData?.urlAva.toString()
                        )
                    )
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        return user.toFlowable(BackpressureStrategy.BUFFER)
    }


    fun signOut() = auth.signOut()


}