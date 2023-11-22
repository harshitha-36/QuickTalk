package com.courseclass.quickchat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable


class ViewPagerAdapter(private val context: Context) : PagerAdapter() {

    private val sliderAllImages = intArrayOf(
        R.raw.connect,
        R.raw.videocall,
        R.raw.chatbot,
        R.raw.mic,
        R.raw.themes
    )

    private val sliderAllTitle = intArrayOf(
        R.string.screen1,
        R.string.screen2,
        R.string.screen3,
        R.string.screen4,
        R.string.screen5

    )

    private val sliderAllDesc = intArrayOf(
        R.string.screenDesc1,
        R.string.screenDesc2,
        R.string.screenDesc3,
        R.string.screenDesc4,
        R.string.screenDesc5

    )

    override fun getCount(): Int {
        return sliderAllTitle.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as View
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.activity_view_pager_adapter, container, false)
        val sliderImage: LottieAnimationView = view.findViewById(R.id.sliderimage)
        val sliderTitle: TextView = view.findViewById(R.id.slidertitle)
        val sliderDesc: TextView = view.findViewById(R.id.sliderDesc)
        val animationResource = sliderAllImages[position]

        sliderImage.setAnimation(animationResource)
        sliderImage.repeatMode = LottieDrawable.RESTART
        sliderImage.repeatCount = LottieDrawable.INFINITE

        sliderTitle.setText(sliderAllTitle[position])
        sliderDesc.setText(sliderAllDesc[position])

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}