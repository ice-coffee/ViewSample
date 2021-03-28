package com.sample.jetpack.databinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sample.jetpack.R

/**
 * @date: 2021/3/27
 * @author: ice_coffee
 * remark:
 */
class DataBindingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentDataBindingBinding>(
            LayoutInflater.from(context),
            R.layout.fragment_data_binding,
            container,
            false
        )
        binding.user = User("Test", "User")
        return binding.root
    }
}