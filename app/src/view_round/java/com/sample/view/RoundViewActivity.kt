package com.sample.view

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import kotlinx.android.synthetic.main.activity_round_view.*

/**
 * @date: 2020/9/6
<p>
 * @author: ice_coffee
<p>
 * @remark:
 */
class RoundViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_view)

        val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, BitmapFactory.decodeResource(resources, R.mipmap.naruto))
        circularBitmapDrawable.cornerRadius = 10000f
        circularBitmapDrawable.alpha = 30
        circularBitmapDrawable.setAntiAlias(true)
        ivView.setImageDrawable(circularBitmapDrawable)
    }
}