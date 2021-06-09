package com.capstone.saba.data.source.remote

import android.util.Log
import com.capstone.saba.domain.model.ChatBot
import com.capstone.saba.domain.model.Todo
import com.capstone.saba.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RemoteDataSource @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth,
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
        urlAva: String,
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

                Log.d("SIGN UP", "${currentUser?.uid} $user")
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
        docRef.addSnapshotListener { document, e ->
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

    fun getNoteTodo(): Flowable<List<Todo>> {
        val todo = PublishSubject.create<List<Todo>>()
        val currentUser = auth.currentUser

        val userId = currentUser?.uid
        //val docRef = db.collection("users").document(userId.toString()).collection("todo").document("untitled")


        db.collection("users").document(userId.toString()).collection("todo").document("untitled")
            .get()
            .addOnSuccessListener { result ->
                val todosList = ArrayList<Todo>()

                    Log.d(TAG, "${result.get("coba")}")

                result.data?.forEach { data ->
                    val todos = Todo(
                        deskripsi = data.value.toString()
                    )
                    todosList.add(todos)

                }
                todo.onNext(todosList)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }



        return todo.toFlowable(BackpressureStrategy.BUFFER)
    }




    fun getChat(): Flowable<List<ChatBot>> {

        val chatBot = PublishSubject.create<List<ChatBot>>()
        val currentUser = auth.currentUser

        val docRefInput = db.collection("chatbot")
            .document(currentUser?.uid.toString())
            .collection("input").orderBy("time", Query.Direction.ASCENDING)


        val docResponse = db.collection("chatbot")
            .document(currentUser?.uid.toString())
            .collection("response").orderBy("timestamp", Query.Direction.ASCENDING)

        Log.d("CURRENT USER", auth.uid!!)

        docRefInput.addSnapshotListener { input, e ->
            docResponse.addSnapshotListener { response, e ->
                val chatbot = ArrayList<ChatBot>()
                try {
                    if (input != null) {
                        for (i in 0..input.documents.size - 1) {
                            val data = ChatBot(
                                input = input.documents.get(i).get("messages").toString(),
                                response = response?.documents?.get(i)?.get("messages").toString()
                            )
                            chatbot.add(data)
                        }
                    }
                } catch (e: Exception) {
                    Log.d("REMOTE ERROR", e.toString())
                }

                chatBot.onNext(chatbot)
            }
        }
        return chatBot.toFlowable(BackpressureStrategy.BUFFER)
    }


    fun sentChat(messages: String): Flowable<Boolean> {
        val result = PublishSubject.create<Boolean>()
        val currentUser = auth.currentUser
        val docRefInput = db.collection("chatbot")
            .document(currentUser?.uid.toString())
            .collection("input")

        docRefInput.add(hashMapOf(
            "messages" to messages,
            "time" to "${Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())}"
        ))
            .addOnSuccessListener {
                result.onNext(true)
            }.addOnFailureListener {
                result.onNext(false)
            }

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

}