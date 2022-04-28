package com.rock.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.rock.data.entity.Banner
import kotlinx.coroutines.flow.Flow

@Dao
abstract class BannerDao : BaseDao<Banner>() {
    @Query("SELECT * FROM Banner")
    abstract fun getBanner(): Flow<List<Banner>>

    @Query("DELETE FROM Banner WHERE id NOT IN (:ids)")
    abstract fun removeCache(ids:List<Int>)
}