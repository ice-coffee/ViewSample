package com.sample.view.tablayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sample.view.R
import kotlinx.android.synthetic.main.fragment_tab_layout.*

/**
 *  @author mzp
 *  date : 2020/11/13
 *  desc :
 */
class TabLayoutFragment(): Fragment() {

    companion object {
        const val TAB_TITLE = "tab_title"

        fun getInstance(tabTitle: String): Fragment {
            val fragment = TabLayoutFragment()
            val bundle = Bundle()
            bundle.putString(TAB_TITLE, tabTitle)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvContent.text = arguments?.getString(TAB_TITLE) ?: ""
    }
}