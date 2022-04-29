package com.rock.data.local.dao

import androidx.room.Dao
import androidx.room.Query

import com.rock.data.entity.RemoteKeys

@Dao
abstract class RemoteKeysDao:BaseDao<RemoteKeys>() {

    @Query("SELECT * FROM RemoteKeys WHERE articleId = :articleId")
    abstract suspend fun getRemoteKeysBy(articleId:Int):RemoteKeys

    @Query("DELETE FROM RemoteKeys")
    abstract suspend fun clearRemoteKeys()
}