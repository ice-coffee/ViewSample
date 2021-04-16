package com.sample.jetpack.databinding

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.common.utils.dp2px
import com.sample.jetpack.R
import kotlinx.android.synthetic.main.activity_data_bind.*

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
        Log.e("MyHandle", "onClickFriend")
    }

    fun onHandleClick(name: String): Boolean {
        Log.e("MyHandle", "onHandleClick, $name")
        return true
    }

    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            Log.e("MyHandle", s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    fun showFocusChange(hasFocus: Boolean) {
        Log.e("MyHandle", "$hasFocus")
    }

    fun afterTextChanged(s: Editable?) {
        Log.e("MyHandle", s.toString())
    }
}

@BindingAdapter("android:layout_marginLeft")
fun setMarginLeft(view: View, margin: Int) {
    Log.e("view_margin", "" + margin)
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