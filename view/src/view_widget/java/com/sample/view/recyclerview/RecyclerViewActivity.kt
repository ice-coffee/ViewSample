package com.sample.view.recyclerview

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sample.view.R

/**
 * date: 2020/11/26
 * author: ice_coffee
 * remark:
 */
class RecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
    }

    fun jumpLayoutManager(view: View) {
        startActivity(Intent(this, LayoutManagerActivity::class.java))
    }

    fun jumpDiffUtil(view: View) {
        startActivity(Intent(this, DiffUtilActivity::class.java))
    }

    fun jumpItemTouchHelper(view: View) {
        startActivity(Intent(this, ItemTouchHelperActivity::class.java))
    }

    fun jumpItemAnimator(view: View) {
        startActivity(Intent(this, ItemAnimatorActivity::class.java))
    }

    fun jumpItemDecoration(view: View) {
        startActivity(Intent(this, ItemDecorationActivity::class.java))
    }
}