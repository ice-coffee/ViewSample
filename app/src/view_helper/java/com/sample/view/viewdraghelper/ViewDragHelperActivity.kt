package com.sample.view.viewdraghelper

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sample.view.R
import kotlinx.android.synthetic.main.activity_view_drag_helper.*

/**
 *  @author mzp
 *  date : 2020/9/28
 *  desc :
 */
class ViewDragHelperActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_drag_helper)

        dragLayout.addDragView(dragView)

        dragView.setOnClickListener { Toast.makeText(this, "click", Toast.LENGTH_SHORT).show() }
    }
}