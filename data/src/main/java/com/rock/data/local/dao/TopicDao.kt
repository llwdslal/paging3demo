package com.rock.data.local.dao


import androidx.room.Dao
import androidx.room.Query
import com.rock.data.entity.Article
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TopicDao:BaseDao<Article>() {
    @Query("SELECT * FROM Article WHERE type = 1 AND id in(:ids)")
    abstract fun getTopics(ids:List<Int>):List<Article>
}