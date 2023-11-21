package com.courseclass.quickchat.model

import com.google.firebase.Timestamp

class ChatMessageModel {

    private lateinit var message: String
    private lateinit var senderId: String
    private lateinit var timestamp: Timestamp

    constructor()

    constructor(message: String, senderId: String, timestamp: Timestamp) {
        this.message = message
        this.senderId = senderId
        this.timestamp = timestamp
    }

    fun getMessage(): String {
        return message
    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun getSenderId(): String {
        return senderId
    }

    fun setSenderId(senderId: String) {
        this.senderId = senderId
    }

    fun getNowTimestamp(): Timestamp {
        return timestamp
    }

    fun setNowTimestamp(timestamp: Timestamp) {
        this.timestamp = timestamp
    }

}