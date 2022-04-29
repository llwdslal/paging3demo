package com.rock.data.di

import androidx.datastore.preferences.core.Preferences
import com.rock.data.local.WanAndroidDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import androidx.datastore.core.DataStore

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun topicDao(db:WanAndroidDB) = db.topicDao()

    @Provides
    @Singleton
    fun bannerDao(db:WanAndroidDB) = db.bannerDao()

    @Provides
    @Singleton
    fun articleDao(db:WanAndroidDB) = db.articleDao()

    @Provides
    @Singleton
    fun remoteKeysDao(db:WanAndroidDB) = db.remoteKeysDao()

}