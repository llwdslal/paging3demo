package com.rock.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.rock.data.entity.Article

@Dao
abstract class ArticleDao: BaseDao<Article>() {

    @Query("SELECT * FROM Article WHERE type = 0 ORDER BY publishTime DESC")
    abstract fun pagingSource():PagingSource<Int,Article>

    @Query("SELECT * FROM Article WHERE type = 0 ORDER BY publishTime DESC LIMIT 1")
    abstract suspend fun firstOrNullArticle():Article?

    @Query("DELETE FROM Article WHERE type = 0")
    abstract suspend fun clearArticle()
}