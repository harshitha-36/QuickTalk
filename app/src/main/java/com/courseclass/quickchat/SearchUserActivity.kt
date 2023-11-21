package com.courseclass.quickchat

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.courseclass.quickchat.adapter.SearchUserRecyclerAdapter
import com.courseclass.quickchat.model.UserModel
import com.courseclass.quickchat.ui.theme.QuickChatTheme
import com.courseclass.quickchat.utils.FirebaseUtil
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query

class SearchUserActivity : ComponentActivity() {

    private lateinit var searchInput: EditText
    private lateinit var searchButton: ImageButton
    private lateinit var backButton: ImageButton
    private lateinit var recyclerView: RecyclerView

    private var adapter: SearchUserRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_user)

        searchInput = findViewById(R.id.serach_username_input)
        searchButton = findViewById(R.id.search_user_btn)
        backButton = findViewById(R.id.back_btn)
        recyclerView = findViewById(R.id.search_user_recycler_view)

        searchInput.requestFocus()

        backButton.setOnClickListener{
            onBackPressed()
        }

        searchButton.setOnClickListener{
            var searchTerm: String = searchInput.text.toString()
            if(searchTerm.isEmpty() || searchTerm.length<3) {
                searchInput.setError("Invalid Username")
                return@setOnClickListener
            }
            setupSearchRecyclerView(searchTerm)
        }
    }

    fun setupSearchRecyclerView(searchTerm: String) {

        val myClassInstance = FirebaseUtil()
        val query: Query = myClassInstance.allUserCollectionReference()
            .whereGreaterThanOrEqualTo("username",searchTerm)

        val options: FirestoreRecyclerOptions<UserModel> = FirestoreRecyclerOptions.Builder<UserModel>()
            .setQuery(query, UserModel::class.java)
            .build()


        adapter = SearchUserRecyclerAdapter(options,applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(this)
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
