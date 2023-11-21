package com.courseclass.quickchat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.courseclass.quickchat.adapter.RecentChatRecyclerAdapter
import com.courseclass.quickchat.adapter.SearchUserRecyclerAdapter
import com.courseclass.quickchat.model.ChatroomModel
import com.courseclass.quickchat.model.UserModel
import com.courseclass.quickchat.utils.FirebaseUtil
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query

class ChatFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecentChatRecyclerAdapter

    class ChatFragment() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_chat, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        setupRecyclerView()

        return view
    }

    fun setupRecyclerView() {

        val myClassInstance = FirebaseUtil()
        val query: Query = myClassInstance.allChatroomCollectionReference()
            .whereArrayContains("users",myClassInstance.currentUserId())
            .orderBy("lastMessageTimestamp",Query.Direction.DESCENDING)

        val options: FirestoreRecyclerOptions<ChatroomModel> = FirestoreRecyclerOptions.Builder<ChatroomModel>()
            .setQuery(query, ChatroomModel::class.java)
            .build()


        adapter = context?.let { RecentChatRecyclerAdapter(options, it) }!!
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        adapter!!.startListening()
    }

    override fun onStart() {
        super.onStart()
        if(adapter!=null) adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        if(adapter!=null) adapter!!.stopListening()
    }

    override fun onResume() {
        super.onResume()
        if(adapter!=null) adapter!!.startListening()
    }
}