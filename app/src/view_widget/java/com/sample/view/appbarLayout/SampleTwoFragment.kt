package com.sample.view.appbarLayout

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.view.R
import kotlinx.android.synthetic.main.fragment_appbar_one.*

/**
 *  @author mzp
 *  date : 2020/9/29
 *  desc :
 */
class SampleTwoFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_appbar_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myList.layoutManager = LinearLayoutManager(context)
        myList.adapter = MyAdapter(context!!)
    }

    class RecyclerViewBehavior : CoordinatorLayout.Behavior<RecyclerView> {

        constructor():super(){}
        constructor(context: Context, attributeSet: AttributeSet):super(context, attributeSet){}


    }
}