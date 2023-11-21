package com.courseclass.quickchat.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.courseclass.quickchat.model.UserModel

class AndroidUtil {

    fun showToast(context: Context,message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun passUserModelAsIntent(intent: Intent, model: UserModel) {
        intent.putExtra("username",model.getUsername())
        intent.putExtra("phone",model.getPhone())
        intent.putExtra("userId",model.getUserId())
    }

    fun getUserModelFromIntent(intent: Intent): UserModel {
        val userModel = UserModel()
        intent.getStringExtra("username")?.let { userModel.setUsername(it) }
        intent.getStringExtra("phone")?.let { userModel.setPhone(it) }
        intent.getStringExtra("userId")?.let { userModel.setUserId(it) }
        return userModel
    }
}