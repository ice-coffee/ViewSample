package com.sample.view.appbarLayout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sample.view.R
import java.util.*

/**
 *  @author mzp
 *  date : 2020/11/13
 *  desc :
 */
class MyAdapter(context: Context) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private val colorArray = arrayOf("#FADB14", "#FFD6E7", "#D9D9D9", "#BAE7FF", "#D3F261", "#BAC5F3", "#FFEADB", "#FF847C", "#D9F7BE", "#FFEECC")
    private val random = Random()
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        return MyAdapter.MyViewHolder(layoutInflater.inflate(R.layout.view_item, parent, false))
    }

    override fun getItemCount(): Int {
        return 15
    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {
        holder.tvItem.text = "item$position"
        holder.tvItem.setBackgroundColor(Color.parseColor(colorArray[random.nextInt(colorArray.size)]))
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvItem: TextView = view.findViewById(R.id.tvItem)
    }
}