package com.capstone.saba.domain.model

data class ChatBot(
    val response: Response,
    val input: Input
)


data class Input(
    val messages: List<String> = listOf("")
)

data class Response(
    val messages: List<Map<String,String>> = listOf(mapOf(
        "" to ""
    ))
)
