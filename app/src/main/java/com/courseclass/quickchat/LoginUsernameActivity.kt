package com.courseclass.quickchat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import com.courseclass.quickchat.model.UserModel
import com.courseclass.quickchat.utils.FirebaseUtil
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot

class LoginUsernameActivity : ComponentActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var signBtn: Button
    private lateinit var progressBar: ProgressBar

    private var phoneNumber: String? = null
    private lateinit var usermodel: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginusername)

        usernameInput = findViewById(R.id.login_username)
        signBtn = findViewById(R.id.login_sign_in_btn)
        progressBar = findViewById(R.id.login_progress_bar)

        phoneNumber = intent.getStringExtra("phone")
        //usermodel = UserModel("", "", Timestamp.now(),"")
        getUsername()

        signBtn.setOnClickListener {
                setUsername()
        }

    }

    fun setUsername() {
        //setInProgressBar(true)
         var username: String = usernameInput.text.toString()
        if(username.isEmpty() || username.length<3)
        {
            usernameInput.error = "Username length should be at least 3 chars"
            return
        }
        setInProgressBar(true)
        if(::usermodel.isInitialized) {
            usermodel.setUsername(username)
        } else {
            val myClassInstance = FirebaseUtil()
            usermodel = UserModel(phoneNumber.toString(), username, Timestamp.now(),myClassInstance.currentUserId())
        }
        val myClassInstance = FirebaseUtil()
        myClassInstance.currentUserDetails().set(usermodel)
            .addOnCompleteListener { task ->
                setInProgressBar(false)
                if (task.isSuccessful) {
                    // Task completed successfully
                    val intent = Intent(this@LoginUsernameActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }

    }

    fun getUsername() {
        setInProgressBar(true)
        val myClassInstance = FirebaseUtil()
        myClassInstance.currentUserDetails().get().addOnCompleteListener { task ->
            setInProgressBar(false)
            if (task.isSuccessful) {
                val retrievedUserModel = task.result?.toObject(UserModel::class.java)
                retrievedUserModel?.let {
                    usermodel = it
                    usernameInput.setText(usermodel.getUsername())
                }
                //val documentSnapshot = task.result
                // Your logic with the documentSnapshot
            }
        }

    }

    fun setInProgressBar(inProgress: Boolean) {
        if (inProgress) {
            progressBar.visibility = View.VISIBLE
            signBtn.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
            signBtn.visibility = View.VISIBLE
        }
    }
}
