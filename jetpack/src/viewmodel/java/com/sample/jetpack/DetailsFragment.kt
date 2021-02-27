package com.sample.jetpack

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sample.view.R
import kotlinx.android.synthetic.main.fragment_details.*

/**
 * @date: 2021/2/27
 * @author: ice_coffee
 * remark:
 */
class DetailsFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(activity!!).get(ShareViewModel::class.java)
        viewModel.clickLiveData.observe(this, Observer {
            textView.text = "${viewModel.clickLiveData.value}"
        })
    }
}