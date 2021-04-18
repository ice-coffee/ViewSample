package com.sample.mvvm.customedittext

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.adapters.ListenerUtil
import com.sample.mvvm.R

/**
 * @date: 2021/4/17
 * @author: ice_coffee
 * remark:
 */
class CustomEditTextBindingAdapter {

    companion object {
        @BindingAdapter("app:customTextAttrChanged")
        @JvmStatic
        fun setChangeListener(view: CustomEditText, attrChange: InverseBindingListener?) {
            val newValue: TextWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    attrChange?.onChange()
                }

                override fun afterTextChanged(s: Editable) {
                }
            }
            val oldValue = ListenerUtil.trackListener(view, newValue, R.id.textWatcher)
            if (oldValue != null) {
                view.removeTextChangedListener(oldValue)
            }
            if (newValue != null) {
                view.addTextChangedListener(newValue)
            }
        }

        @BindingAdapter("app:customText")
        @JvmStatic
        fun setCustomText(view: CustomEditText, text: String) {
            val oldText: CharSequence? = view.text
            if (TextUtils.equals(text, oldText)) {
                return
            }
            view.setText(text)
        }

        @InverseBindingAdapter(attribute = "app:customText", event = "app:customTextAttrChanged")
        @JvmStatic
        fun getCustomText(view: CustomEditText): String {
            return view.text.toString()
        }
    }
}