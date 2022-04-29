package com.rock.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKeys(
    @PrimaryKey
    val articleId:Int,
    val prevKey :Int?,
    val nextKey :Int?
)