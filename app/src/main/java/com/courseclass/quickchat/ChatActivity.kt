package com.courseclass.quickchat

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.courseclass.quickchat.adapter.ChatRecyclerAdapter
import com.courseclass.quickchat.adapter.SearchUserRecyclerAdapter
import com.courseclass.quickchat.model.ChatMessageModel
import com.courseclass.quickchat.model.ChatroomModel
import com.courseclass.quickchat.model.UserModel
import com.courseclass.quickchat.ui.theme.QuickChatTheme
import com.courseclass.quickchat.utils.AndroidUtil
import com.courseclass.quickchat.utils.FirebaseUtil
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import java.util.Arrays
import java.util.Arrays.asList

class ChatActivity : AppCompatActivity() {

    private lateinit var otherUser: UserModel
    private lateinit var chatroomId: String
    private lateinit var chatroomModel: ChatroomModel
    private lateinit var chatMessageModel: ChatMessageModel
    private lateinit var chatAdapter: ChatRecyclerAdapter

    private lateinit var messageInput: EditText
    private lateinit var sendMessageBtn: ImageButton
    private lateinit var backBtn: ImageButton
    private lateinit var otherUsername: TextView
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val myAndroidInstance = AndroidUtil()
        otherUser = myAndroidInstance.getUserModelFromIntent(intent)
        val myFirebaseInstance = FirebaseUtil()
        chatroomId = myFirebaseInstance.getChatroomId(myFirebaseInstance.currentUserId(),otherUser.getUserId())

        messageInput = findViewById(R.id.chat_message_input)
        sendMessageBtn = findViewById(R.id.message_send_btn)
        backBtn = findViewById(R.id.back_btn)
        otherUsername = findViewById(R.id.other_username)
        recyclerView = findViewById(R.id.chat_recycler_view)

        backBtn.setOnClickListener{
            onBackPressed()
        }
        otherUsername.text = otherUser.getUsername()

        sendMessageBtn.setOnClickListener {it ->
            run {
                val message: String = messageInput.text.toString()
                // Call the function to send the message
                if (message.isEmpty()) return@setOnClickListener// Return if the message is empty
                else sendMessageToUser(message)
            }
        }

        getOrCreateChatroomModel()
        setupChatRecyclerView()
    }

    /*fun setupChatRecyclerView() {
        val myClassInstance = FirebaseUtil()
        val query: Query = myClassInstance.getChatroomMessageReference(chatroomId).orderBy("timestamp", Query.Direction.DESCENDING)

        val options = FirestoreRecyclerOptions.Builder<ChatMessageModel>()
            .setQuery(query, ChatMessageModel::class.java)
            .build()

        chatAdapter = ChatRecyclerAdapter(options, applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(this).apply {
            reverseLayout = true
        }
        recyclerView.adapter = chatAdapter
        chatAdapter!!.startListening()

        query.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documentSnapshot = task.result
                if (documentSnapshot != null && !documentSnapshot.isEmpty) {
                    // Notify adapter of data set change
                    chatAdapter.notifyDataSetChanged()
                }
            }
        }
    }*/


    fun setupChatRecyclerView() {
        val myClassInstance = FirebaseUtil()
        val query: Query = myClassInstance.getChatroomMessageReference(chatroomId).orderBy("timestamp",Query.Direction.DESCENDING)
        //Toast.makeText(applicationContext,query.toString(),Toast.LENGTH_LONG).show()
        val options = FirestoreRecyclerOptions.Builder<ChatMessageModel>()
            .setQuery(query, ChatMessageModel::class.java)
            .build()

        //Toast.makeText(applicationContext,"Recycler view",Toast.LENGTH_LONG).show()
        chatAdapter = ChatRecyclerAdapter(options,applicationContext)
        //Toast.makeText(applicationContext,"chatAdapter",Toast.LENGTH_LONG).show()
        //var manager: LinearLayoutManager = LinearLayoutManager(this)
        //manager.reverseLayout = true
        recyclerView.layoutManager = LinearLayoutManager(this).apply {
            reverseLayout = true
            //Toast.makeText(applicationContext,"Reverse layout",Toast.LENGTH_LONG).show()
        }
        recyclerView.adapter = chatAdapter
        //Toast.makeText(applicationContext,"readapter",Toast.LENGTH_LONG).show()
        chatAdapter!!.startListening()
        chatAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                // Your logic for handling item range insertion
                recyclerView.smoothScrollToPosition(0)
            }
        })
        //this.chatAdapter = chatAdapter
    }

    override fun onStart() {
        super.onStart()
        if(chatAdapter!=null) chatAdapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        if(chatAdapter!=null) chatAdapter!!.stopListening()
    }

    override fun onResume() {
        super.onResume()
        if(chatAdapter!=null) chatAdapter!!.startListening()
    }

    fun sendMessageToUser(message: String) {

        val myFirebaseInstance = FirebaseUtil()
        chatroomModel.setlastMessageTimestamp(Timestamp.now())
        chatroomModel.setLastMessageSenderId(myFirebaseInstance.currentUserId())
        //Toast.makeText(applicationContext,chatroomModel.userIds.toString(), Toast.LENGTH_LONG).show()
        myFirebaseInstance.getChatroomReference(chatroomId).set(chatroomModel)

        var messageModel = ChatMessageModel(message,myFirebaseInstance.currentUserId(),
            Timestamp.now())
        myFirebaseInstance.getChatroomMessageReference(chatroomId)
            .add(messageModel)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    messageInput.setText("")
                }
            }
        this.chatMessageModel = messageModel
    }

    fun getOrCreateChatroomModel() {
        val myFirebaseInstance = FirebaseUtil()
        myFirebaseInstance.getChatroomReference(chatroomId).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var chatModel = task.result.toObject(ChatroomModel::class.java)
                if (chatModel == null) {
                    //Toast.makeText(applicationContext,chatroomId, Toast.LENGTH_LONG).show()
                    chatModel = ChatroomModel(
                        chatroomId,
                        listOf(myFirebaseInstance.currentUserId(), otherUser.getUserId()),
                        Timestamp.now(),
                        ""
                    )
                    this.chatroomModel = chatModel
                    //Toast.makeText(applicationContext,chatroomModel.userIds.toString(), Toast.LENGTH_LONG).show()
                    myFirebaseInstance.getChatroomReference(chatroomId).set(chatroomModel)
                }
                else {
                    //Toast.makeText(applicationContext,chatroomId, Toast.LENGTH_LONG).show()
                    chatModel = ChatroomModel(
                        chatroomId,
                        listOf(myFirebaseInstance.currentUserId(), otherUser.getUserId()),
                        Timestamp.now(),
                        ""
                    )
                    this.chatroomModel = chatModel
                    //Toast.makeText(applicationContext,"chatroom not null", Toast.LENGTH_LONG).show()
                    myFirebaseInstance.getChatroomReference(chatroomId).set(chatroomModel)
                }
            }
        }
    }
}