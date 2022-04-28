package com.rock.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class TopicPDS @Inject constructor(
    private val dataStore: DataStore<Preferences>
){
    private val Key_TopicIds  = stringPreferencesKey("TopicIds")

    suspend fun saveTopicIds(ids:List<Int>){
        val idsStr = ids.joinToString(separator = ",") {it.toString()}
        dataStore.edit {
            it[Key_TopicIds] = idsStr
        }
    }

    fun getTopicIds():Flow<List<Int>>{
        return  dataStore.data.map {
            it[Key_TopicIds] ?: ""
        }.map { ids ->
            if (ids.isNotEmpty()){
                ids.split(",").map { it.toInt() }
            }else{
                emptyList()
            }
        }
    }

}