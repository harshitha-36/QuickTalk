package com.courseclass.quickchat.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.courseclass.quickchat.ChatActivity
import com.courseclass.quickchat.MainActivity
import com.courseclass.quickchat.R
import com.courseclass.quickchat.model.ChatroomModel
import com.courseclass.quickchat.model.UserModel
import com.courseclass.quickchat.utils.AndroidUtil
import com.courseclass.quickchat.utils.FirebaseUtil
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import org.checkerframework.checker.nullness.qual.NonNull


class RecentChatRecyclerAdapter(options: FirestoreRecyclerOptions<ChatroomModel>, context: Context) : FirestoreRecyclerAdapter<ChatroomModel, RecentChatRecyclerAdapter.ChatroomModelViewHolder>(options) {

    private val context: Context
    init {
        this.context = context
    }

    class ChatroomModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val usernameText: TextView = itemView.findViewById(R.id.user_name_text)
        val lastMessageText: TextView = itemView.findViewById(R.id.phone_text)
        val lastMessageTime: TextView = itemView.findViewById(R.id.last_message_time_text)
        val profilePic: ImageView = itemView.findViewById(R.id.profile_pic_image_view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatroomModelViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recent_chat_recycler_row, parent, false)
        return ChatroomModelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatroomModelViewHolder, position: Int, model: ChatroomModel) {

    }
}