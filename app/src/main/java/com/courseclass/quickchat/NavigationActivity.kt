package com.courseclass.quickchat

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager

class NavigationActivity : AppCompatActivity() {

    private lateinit var slideViewPager: ViewPager
    private lateinit var dotIndicator: LinearLayout
    private lateinit var backButton: Button
    private lateinit var nextButton: Button
    private lateinit var skipButton: Button
    private lateinit var dots: Array<TextView>
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    private val viewPagerListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            setDotIndicator(position)
            backButton.visibility = if (position > 0) View.VISIBLE else View.INVISIBLE
            nextButton.text = if (position == 2) "Finish" else "Next"
        }

        override fun onPageScrollStateChanged(state: Int) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        backButton = findViewById(R.id.backBtn)
        nextButton = findViewById(R.id.nextBtn)
        skipButton = findViewById(R.id.skipBtn)

        backButton.setOnClickListener {
            if (getItem(0) > 0) {
                slideViewPager.setCurrentItem(getItem(-1), true)
            }
        }

        nextButton.setOnClickListener {
            val totalSlides = 5 // Set the total number of slides here

            if (getItem(0) < totalSlides - 1)
                slideViewPager.setCurrentItem(getItem(1), true)
            else {
                val i = Intent(this@NavigationActivity, GetStarted::class.java)
                startActivity(i)
                finish()
            }
        }


        skipButton.setOnClickListener {
            val i = Intent(this@NavigationActivity, LoginPhoneActivity::class.java)
            startActivity(i)
            finish()
        }

        slideViewPager = findViewById(R.id.slideViewPager)
        dotIndicator = findViewById(R.id.dotIndicator)

        viewPagerAdapter = ViewPagerAdapter(this)
        slideViewPager.adapter = viewPagerAdapter

        setDotIndicator(0)
        slideViewPager.addOnPageChangeListener(viewPagerListener)
    }

    private fun setDotIndicator(position: Int) {
        dots = Array(5) { TextView(this) }
        dotIndicator.removeAllViews()

        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i].text = Html.fromHtml("&#8226", Html.FROM_HTML_MODE_LEGACY)
            dots[i].textSize = 35f
            dots[i].setTextColor(resources.getColor(R.color.grey, applicationContext.theme))
            dotIndicator.addView(dots[i])
        }

        dots[position]?.setTextColor(resources.getColor(R.color.grey, applicationContext.theme))
    }

    private fun getItem(i: Int): Int {
        return slideViewPager.currentItem + i
    }
}
