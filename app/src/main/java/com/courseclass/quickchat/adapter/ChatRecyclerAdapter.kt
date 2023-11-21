package com.courseclass.quickchat.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.courseclass.quickchat.ChatActivity
import com.courseclass.quickchat.MainActivity
import com.courseclass.quickchat.R
import com.courseclass.quickchat.model.ChatMessageModel
import com.courseclass.quickchat.model.UserModel
import com.courseclass.quickchat.utils.AndroidUtil
import com.courseclass.quickchat.utils.FirebaseUtil
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import org.checkerframework.checker.nullness.qual.NonNull


class ChatRecyclerAdapter(options: FirestoreRecyclerOptions<ChatMessageModel>, context: Context) : FirestoreRecyclerAdapter<ChatMessageModel, ChatRecyclerAdapter.ChatModelViewHolder>(options) {

    private val context: Context
    init {
        this.context = context
    }

    class ChatModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val leftChatTextView: TextView = itemView.findViewById(R.id.left_chat_textview)
        val rightChatTextView: TextView = itemView.findViewById(R.id.right_chat_textview)
        val leftChatLayout: LinearLayout = itemView.findViewById(R.id.left_chat_layout)
        val rightChatLayout: LinearLayout = itemView.findViewById(R.id.right_chat_layout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatModelViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.chat_message_row, parent, false)
        return ChatModelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatModelViewHolder, position: Int, model: ChatMessageModel) {
        val myFirebaseInstance = FirebaseUtil()
        if(model.getSenderId() == myFirebaseInstance.currentUserId()) {
            holder.leftChatLayout.visibility = View.GONE
            holder.rightChatLayout.visibility = View.VISIBLE
            holder.rightChatTextView.text = model.getMessage()
        } else {
            holder.rightChatLayout.visibility = View.GONE
            holder.leftChatLayout.visibility = View.VISIBLE
            holder.leftChatTextView.text = model.getMessage()
        }
    }
}