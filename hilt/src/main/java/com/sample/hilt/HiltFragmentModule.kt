package com.sample.hilt

import com.sample.hilt.bean.OutSideBean
import com.sample.hilt.bean.SchoolBean
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
    fun provideSchoolBean(outSideBean: OutSideBean): SchoolBean {
        return SchoolBean(outSideBean)
    }
}