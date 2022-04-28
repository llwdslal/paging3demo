package com.rock.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.rock.data.entity.Article

@Dao
abstract class ArticleDao: BaseDao<Article>() {

    @Query("SELECT * FROM Article WHERE type = 0")
    abstract fun pagingSource():PagingSource<Int,Article>
}