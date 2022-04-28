package com.rock.data.di

import androidx.paging.PagingConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
class PagingModule {
    @Provides
    @ViewModelScoped
    fun providePagingConfig():PagingConfig = PagingConfig(pageSize = 20)
}