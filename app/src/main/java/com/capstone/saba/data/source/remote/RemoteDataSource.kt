package com.capstone.saba.data.source.remote

import android.util.Log
import com.capstone.saba.domain.model.Todo
import com.capstone.saba.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject
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


    fun signUpWithEmail(
        email: String,
        password: String,
        name: String,
        gender: String,
        birthOfDate: String,
        urlAva: String
    ): Flowable<Boolean> {
        val result = PublishSubject.create<Boolean>()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                val currentUser = auth.currentUser
                val user = User(
                    id = currentUser?.uid.toString(),
                    email = email,
                    gender = gender,
                    name = name,
                    birthOfDate = birthOfDate,
                    urlAva = urlAva,
                    password = password
                )

                Log.d("SIGN UP","${currentUser?.uid} $user")
                currentUser?.uid?.let {
                    db.collection("users").document(it)
                        .set(user)
                        .addOnSuccessListener {
                            result.onNext(true)

                        }
                        .addOnFailureListener { e ->
                            result.onNext(false)
                            Log.w(TAG, "Error writing document", e)
                        }
                }
            }
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun signInWithEmail(email: String, password: String): Flowable<Boolean> {

        val result = PublishSubject.create<Boolean>()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                result.onNext(task.isSuccessful)

            }

        return result.toFlowable(BackpressureStrategy.BUFFER)

    }

    fun getDataUser(): Flowable<User> {
        val user = PublishSubject.create<User>()
        val currentUser = auth.currentUser

        val userId = currentUser?.uid
        val docRef = db.collection("users").document(userId.toString())
        docRef.addSnapshotListener{ document,e ->
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


        return user.toFlowable(BackpressureStrategy.BUFFER)
    }


    fun signOut() = auth.signOut()

    fun getNoteTodo(): Flowable<Todo> {
        val todo = PublishSubject.create<Todo>()
        val currentUser = auth.currentUser

        val userId = currentUser?.uid
        val docRef = db.collection("users").document(userId.toString())
        docRef.addSnapshotListener{ document,e ->
            if (document != null) {
                Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                Log.d("REMOTE DATA TODO", document.get("note").toString().get(1).toString())
                todo.onNext()
            } else {
                Log.d(TAG, "No such document")
            }
        }


        return todo.toFlowable(BackpressureStrategy.BUFFER)
    }



}