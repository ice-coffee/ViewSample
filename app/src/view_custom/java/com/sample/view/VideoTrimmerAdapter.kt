package com.sample.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 *  @author mzp
 *  date : 2020/11/4
 *  desc :
 */
class VideoTrimmerAdapter(private var context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(context).inflate(if (viewType == 1) R.layout.item_empty_adapter else R.layout.item_adapter, parent, false)
        return MyViewholder(view)
    }

    override fun getItemCount(): Int {
        return 12
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0 || position == 11) {
            return 1
        }
        return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    class MyViewholder(view: View) : RecyclerView.ViewHolder(view) {

    }
}