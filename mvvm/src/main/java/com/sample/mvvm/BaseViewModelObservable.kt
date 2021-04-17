package com.sample.mvvm

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel

/**
 * @date: 2021/4/17
 * @author: ice_coffee
 * remark:
 */
abstract class BaseViewModelObservable : ViewModel(), Observable {
    private val mCallbacks: PropertyChangeRegistry = PropertyChangeRegistry()

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        mCallbacks.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        mCallbacks.add(callback)
    }

    fun notifyChange() {
        mCallbacks.notifyCallbacks(this, 0, null)
    }

    fun notifyPropertyChanged(fieldId: Int) {
        mCallbacks.notifyCallbacks(this, fieldId, null)
    }
}