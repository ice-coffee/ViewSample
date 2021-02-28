package com.sample.jetpack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sample.common.BaseApplication
import com.sample.view.R
import kotlinx.android.synthetic.main.fragment_master.*

/**
 * @date: 2021/2/27
 * @author: ice_coffee
 * remark:
 */
class MasterFragment: Fragment() {

    private lateinit var viewModel: ShareViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_master, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(activity!!, ViewModelProvider.AndroidViewModelFactory(BaseApplication.instance!!)).get(ShareViewModel::class.java)

        button.setOnClickListener {
            Thread(Runnable {
                val value = viewModel.clickLiveData.value
                viewModel.clickLiveData.postValue(value?.plus(1) ?: 0)
            }).start()
        }
    }
}