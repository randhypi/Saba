package com.capstone.saba.data.source.remote

import android.util.Log
import com.capstone.saba.domain.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
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

                /*val list = result

                val todos = Todo(
                    deskripsi = list.toString()
                )
                todosList.add(todos)
                todo.onNext(todosList)*/

            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

    /*docRef.addSnapshotListener { document, e ->
            val todosList = ArrayList<Todo>()
            if (document != null) {
                Log.d(TAG, "DocumentSnapshot data: ${document.data?.get("coba")}")
                //val data = document.toObject<Todo>()

                val da = document.get("coba")

                Log.d("REMOTE DATA TODO", da.toString())
                document.data?.get("coba"). { (s, any) ->
                   val todos = Todo(
                       deskripsi = s
                   )
                    todosList.add(todos)
                }
                todo.onNext(todosList)
            } else {
                Log.d(TAG, "No such document")
            }
        }
*/

        return todo.toFlowable(BackpressureStrategy.BUFFER)
    }




    fun getChat(): Flowable<List<ChatBot>> {

        val chatBot = PublishSubject.create<List<ChatBot>>()
        val currentUser = auth.currentUser

        val docRefInput = db.collection("chatbot")
            .document("input")
            .collection("from-user")
            .document(currentUser?.uid.toString())

        val docResponse = db.collection("chatbot")
            .document("response")
            .collection("for-user")
            .document(currentUser?.uid.toString())

        Log.d("CURRENT USER", auth?.uid!!)

        docRefInput.addSnapshotListener { input, e ->
            docResponse.addSnapshotListener { response, e ->
                val responseObject = response?.toObject<Response>()
                val inputObject = input?.toObject<Input>()
                val chatbot = ArrayList<ChatBot>()
                inputObject?.let {
                    responseObject?.let { it1 ->
                        Log.d("ChatBot", "$it\n $it1")
                        try {
                            for (i in 0..it.messages.size) {
                                it1.messages.get(i).get("response")?.let { it2 ->
                                    val data = ChatBot(
                                        input = it.messages.get(i),
                                        response = it2
                                    )
                                    chatbot.addAll(listOf(data))
                                }
                            }
                        }catch(e: Exception) {
                            Log.d("REMOTE ERROR", e.toString())
                        }
                    }
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
            .document("input")
            .collection("from-user")
            .document(currentUser?.uid.toString())


        currentUser?.uid?.let {
            docRefInput.update("messages", FieldValue.arrayUnion(messages))
                .addOnSuccessListener {
                    result.onNext(true)
                }.addOnFailureListener {
                    result.onNext(false)
                }
        }

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

}