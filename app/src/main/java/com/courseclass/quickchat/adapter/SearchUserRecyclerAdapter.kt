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
import com.courseclass.quickchat.model.UserModel
import com.courseclass.quickchat.utils.AndroidUtil
import com.courseclass.quickchat.utils.FirebaseUtil
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import org.checkerframework.checker.nullness.qual.NonNull


class SearchUserRecyclerAdapter(options: FirestoreRecyclerOptions<UserModel>, context: Context) : FirestoreRecyclerAdapter<UserModel, SearchUserRecyclerAdapter.UserModelViewHolder>(options) {

    private val context: Context
    init {
        this.context = context
    }

    class UserModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val usernameText: TextView = itemView.findViewById(R.id.user_name_text)
        val phoneText: TextView = itemView.findViewById(R.id.phone_text)
        val profilePic: ImageView = itemView.findViewById(R.id.profile_pic_image_view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserModelViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.search_user_recycler_row, parent, false)
        return UserModelViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserModelViewHolder, position: Int, model: UserModel) {
        holder.usernameText.text= model.getUsername()
        holder.phoneText.text = model.getPhone()
        val myClassInstance = FirebaseUtil()
        if(model.getUserId() == myClassInstance.currentUserId()) {
            val username = model.getUsername()
            val me: String = " (Me)"
            holder.usernameText.text = username.plus(me)
        }
        holder.itemView.setOnClickListener { v ->
            run {
                val intent = Intent(context, ChatActivity::class.java)
                val myClassInstance = AndroidUtil()
                myClassInstance.passUserModelAsIntent(intent,model)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        }
    }
}