package com.courseclass.quickchat.model

import android.widget.Toast
import com.google.firebase.Timestamp

class ChatroomModel {
    private lateinit var chatRoomId: String
    lateinit var userIds: List<String>
    private lateinit var lastMessageTimestamp: Timestamp
    private lateinit var lastMessageSenderId: String

    constructor()

    constructor(chatroomId: String, userIds: List<String>, lastMessageTimestamp: Timestamp, lastMessageSenderId: String) {
        this.chatRoomId = chatroomId
        this.userIds = userIds
        this.lastMessageTimestamp = lastMessageTimestamp
        this.lastMessageSenderId = lastMessageSenderId
    }

    fun getChatRoomId(): String {
        return chatRoomId
    }

    fun setChatRoomId(chatRoomId: String) {
        this.chatRoomId = chatRoomId
    }

    fun getUserIdList(): List<String> {
        return userIds
    }

    fun setUserIdList(userIds: List<String>) {
        this.userIds = userIds
    }

    fun getlastMessageTimestamp(): Timestamp {
        return lastMessageTimestamp
    }

    fun setlastMessageTimestamp(lastMessageTimestamp: Timestamp) {
        this.lastMessageTimestamp = lastMessageTimestamp
    }

    fun setLastMessageSenderId(): String {
        return lastMessageSenderId
    }

    fun setLastMessageSenderId(lastMessageSenderId: String) {
        this.lastMessageSenderId = lastMessageSenderId
    }

}