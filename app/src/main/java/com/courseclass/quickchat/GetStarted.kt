package com.courseclass.quickchat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.courseclass.quickchat.LoginPhoneActivity
import com.courseclass.quickchat.R

class GetStarted : AppCompatActivity() {
    private lateinit var startButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)

        startButton = findViewById(R.id.startBtn)
        startButton.setOnClickListener {
            val i = Intent(this@GetStarted, LoginPhoneActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}