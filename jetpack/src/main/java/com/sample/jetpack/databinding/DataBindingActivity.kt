package com.sample.jetpack.databinding

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.jetpack.R
import kotlinx.android.synthetic.main.activity_data_bind.*
import androidx.databinding.library.baseAdapters.BR

/**
 * @date: 2021/3/27
 * @author: ice_coffeeaw2
 * remark:
 */
class DataBindingActivity : AppCompatActivity() {
    private val itemColorList = mutableListOf(
            Item("#CD950C"),
            Item("#8B658B"),
            Item("#FFC1C1"),
            Item("#EEB4B4"),
            Item("#CD9B9B"),
            Item("#8B6969"),
            Item("#FF6A6A"),
            Item("#EE6363"),
            Item("#CD5555")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
                DataBindingUtil.setContentView<ActivityDataBindBinding>(this, R.layout.activity_data_bind)
        binding.user = User("Test", "User")
        binding.list = listOf("One", "Two")
        binding.handle = MyHandle()

        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.llFragment, DataBindingFragment())
        ft.commit()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MainAdapter(this, itemColorList)
    }
}

class MyHandle {
    fun onClickFriend(view: View) {
        Log.e("click", "onClickFriend")
    }

    fun onHandleClick(): Boolean {
        Log.e("click", "onHandleClick")
        return true
    }
}

class MainAdapter(private val context: Context, private val colorList: List<Item>) :
        RecyclerView.Adapter<MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return colorList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.binding.item = colorList[position]
        holder.binding.executePendingBindings()
    }
}

class MainViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)