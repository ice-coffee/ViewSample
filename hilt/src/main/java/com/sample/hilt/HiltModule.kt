package com.sample.hilt

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
object HiltModule {

    @Provides
    fun provideOutSideBean(): OutSideBean {
        return OutSideBean()
    }
}