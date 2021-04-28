package com.sample.hilt

import com.sample.hilt.bean.WaterBean
import com.sample.hilt.bean.CapBean
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

/**
 *  @author mzp
 *  date : 2021/4/27
 *  desc :
 */
@Module
@InstallIn(FragmentComponent::class)
object HiltFragmentModule {

    @Provides
    fun provideCapBean(waterBean: WaterBean): CapBean {
        return CapBean(waterBean)
    }
}