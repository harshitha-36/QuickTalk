package com.courseclass.quickchat

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.airbnb.lottie.LottieAnimationView
import com.courseclass.quickchat.R

class IntroActivity : AppCompatActivity() {

    private lateinit var mlogo: ImageView
    private lateinit var mimg: ImageView
    private lateinit var title: ImageView
    private lateinit var lottieanimationView: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        mlogo = findViewById(R.id.logo)
        mimg = findViewById(R.id.img)
        title = findViewById(R.id.titleapp)
        lottieanimationView = findViewById(R.id.lottie)

        // Get the screen height
        val screenHeight = resources.displayMetrics.heightPixels.toFloat()

        // Create ObjectAnimators for translation animations
        val imgAnimator = ObjectAnimator.ofFloat(mimg, "translationY", -screenHeight)
        val logoAnimator = ObjectAnimator.ofFloat(mlogo, "translationY", screenHeight)
        val titleAnimator = ObjectAnimator.ofFloat(title, "translationY", screenHeight)
        val lottieAnimator = ObjectAnimator.ofFloat(lottieanimationView, "translationY", screenHeight)

        // Create an AnimatorSet to play animations sequentially
        val animatorSet = AnimatorSet()
        animatorSet.play(imgAnimator).with(logoAnimator).with(titleAnimator).with(lottieAnimator)
        animatorSet.duration = 1000
        animatorSet.startDelay = 4000

        // Set a listener to start NavigationActivity after animations finish
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                // Start NavigationActivity after animations finish
                val intent = Intent(this@IntroActivity, NavigationActivity::class.java)
                startActivity(intent)
                finish()
            }
        })


        // Start the animations
        animatorSet.start()
    }
}