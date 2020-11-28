package com.sample.view.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.sample.view.R
import com.sample.view.appbarLayout.MyAdapter
import kotlinx.android.synthetic.main.activity_recycler_view.*

/**
 * date: 2020/11/26
 * author: ice_coffee
 * remark:
 */
public class RecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        recyclerView.layoutManager = GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false)
//        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter(this)
        recyclerView.addItemDecoration(LayoutItemDecoration(10, false))
    }
}