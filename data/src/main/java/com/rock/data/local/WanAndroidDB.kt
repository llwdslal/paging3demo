package com.rock.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rock.data.local.dao.BannerDao
import com.rock.data.entity.Article
import com.rock.data.entity.Banner
import com.rock.data.local.dao.ArticleDao
import com.rock.data.local.dao.TopicDao

@Database(
    entities = [
        Article::class,
        Banner::class
    ],
    version = 1
)
abstract class WanAndroidDB : RoomDatabase(){
    abstract fun topicDao(): TopicDao
    abstract fun bannerDao():BannerDao
    abstract fun articleDao():ArticleDao


    companion object{
        private val instance:WanAndroidDB?= null

        @Synchronized
        fun getDB(context:Context):WanAndroidDB{
            return instance ?: buildDB(context)
        }

        private fun buildDB(context:Context):WanAndroidDB{
            val builder = Room.databaseBuilder(
                context,
                WanAndroidDB::class.java,
                "wanAndroidDB"
            ).fallbackToDestructiveMigration()
            return builder.build()
        }


    }

}