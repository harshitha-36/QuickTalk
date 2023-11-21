package com.courseclass.quickchat

import android.content.Intent
import android.os.Bundle
import android.os.Handler
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
import com.courseclass.quickchat.ui.theme.QuickChatTheme
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.delay

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.hide()

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, LoginPhoneActivity::class.java))
            finish()
        }, 3000)
    }
}
