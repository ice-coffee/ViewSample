package com.sample.hilt

import com.sample.hilt.bean.OutSideBean
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 *  @author mzp
 *  date : 2021/4/27
 *  desc :
 */
@Module
@InstallIn(ActivityComponent::class)
object HiltActivityModule {

    @Provides
    fun provideOutSideBean(): OutSideBean {
        return OutSideBean()
    }
}