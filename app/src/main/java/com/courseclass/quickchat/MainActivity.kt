package com.courseclass.quickchat

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.courseclass.quickchat.R.id.menu_chat


class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var searchButton: ImageButton

    var chatFragment: ChatFragment? = null
    var profileFragment: ProfileFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val chatFragment = ChatFragment()
        val profileFragment = ProfileFragment()

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        searchButton = findViewById(R.id.main_search_btn)

        searchButton.setOnClickListener{
            val intent = Intent(this@MainActivity, SearchUserActivity::class.java)
            startActivity(intent)
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            if (item.itemId == menu_chat) {
                // Your logic when the selected item's ID is R.id.menu_chat
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frame_layout, chatFragment)
                    .commit()
            }
            if (item.itemId == R.id.menu_profile) {
                // Your logic when the selected item's ID is R.id.menu_chat
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frame_layout, profileFragment)
                    .commit()
            }
            if(item.itemId == R.id.menu_vcall) {
                val intent = Intent(this@MainActivity, VideoCallMainActivity::class.java)
                startActivity(intent)
            }
            true // Return true to indicate the item is selected
        }

        bottomNavigationView.selectedItemId = R.id.menu_chat
    }
}