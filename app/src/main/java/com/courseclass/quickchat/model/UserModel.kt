package com.courseclass.quickchat.model

import com.google.firebase.Timestamp

class UserModel {
    private var phone: String = ""
    private var username: String = ""
    private var createdTimestamp: Timestamp = Timestamp.now()
    private var userId: String = ""

    constructor()

    constructor(phone: String, username: String, createdTimestamp: Timestamp,userId: String) {
        this.phone = phone
        this.username = username
        this.createdTimestamp = createdTimestamp
        this.userId = userId
    }

    fun getPhone(): String {
        return phone
    }

    fun setPhone(phone: String) {
        this.phone = phone
    }

    fun getUsername(): String {
        return username
    }

    fun setUsername(username: String) {
        this.username = username
    }

    fun getTimestamp(): Timestamp {
        return createdTimestamp
    }

    fun setTimestamp(createdTimestamp: Timestamp) {
        this.createdTimestamp = createdTimestamp
    }

    fun getUserId(): String {
        return userId
    }

    fun setUserId(userId: String) {
        this.userId = userId
    }
}