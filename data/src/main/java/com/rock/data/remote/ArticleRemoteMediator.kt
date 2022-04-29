package com.rock.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.rock.data.entity.Article
import com.rock.data.entity.RemoteKeys
import com.rock.data.local.WanAndroidDB
import com.rock.data.local.dao.ArticleDao
import com.rock.data.local.dao.RemoteKeysDao
import com.rock.lib_base.net.handleResponse
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.*
import javax.inject.Inject


private const val TAG = "ArticleRemoteMediator"
@OptIn(ExperimentalPagingApi::class)
@ViewModelScoped
class ArticleRemoteMediator @Inject constructor(
    val articleDao: ArticleDao,
    private val db: WanAndroidDB,
    private val remoteKeysDao: RemoteKeysDao,
    private val articleService:WanAndroidService,
):RemoteMediator<Int,Article>() {



    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {
        val page = when(loadType){
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                val page = remoteKeys?.nextKey?.minus(1) ?: 0
                Log.e(TAG, "load: LoadType.REFRESH:$page", )
                page
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)

//                val prevKey = remoteKeys?.prevKey
//                if (prevKey == null){
//                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
//                }
                // 变体 ↓

                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                Log.e(TAG, "load: LoadType.PREPEND:$prevKey", )
                prevKey
            }
            LoadType.APPEND ->{
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                Log.e(TAG, "load: LoadType.APPEND:$nextKey", )
                nextKey
            }
        }


        try {
            val pagedArticles = articleService.getArticle(page,state.config.pageSize).handleResponse { it }
            val articles = pagedArticles.datas
            val endOfPaginationReached = articles.isEmpty()
            db.withTransaction {
                if (loadType == LoadType.REFRESH){
                    remoteKeysDao.clearRemoteKeys()
                    articleDao.clearArticle()
                }

                val prevKey = if (page == 0) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                Log.e(TAG, "load:withTransaction  prevKey=$prevKey <> next = $nextKey", )
                val keys = articles.map { article ->  RemoteKeys(article.id,prevKey,nextKey)}
                remoteKeysDao.insertAll(keys)
                articleDao.insertAll(articles)
            }
            return MediatorResult.Success(endOfPaginationReached)
        }catch (e:Exception){
            if (e is CancellationException){
                throw e
            }
            return MediatorResult.Error(e)
        }
    }

    /*
    首次加载数据时或者调用 PagingDataAdapter.refresh() 时，LoadType.REFRESH 都会被调用，所以现在对于加载数据，引用点是 state.anchorPosition。
    如果这是第一次加载，则 anchorPosition 为 null。
    当调用 PagingDataAdapter.refresh() 时，anchorPosition 是所显示列表中的第一个可见位置，因此我们需要加载包含该特定项的页面。
     */
    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Article>):RemoteKeys?{
      return  state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { articleId ->
                remoteKeysDao.getRemoteKeysBy(articleId)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Article>):RemoteKeys?{

        val lastPage = state.pages.lastOrNull()
        val lastArticle = lastPage?.data?.lastOrNull()
        Log.e(TAG, "getRemoteKeyForLastItem: ${lastArticle?.id}", )
        val lastKey = lastArticle?.let {
            remoteKeysDao.getRemoteKeysBy(it.id)
        }
        return  lastKey

//        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
//            ?.let {
//            remoteKeysDao.getRemoteKeysBy(it.id)
//        }
    }
    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Article>):RemoteKeys?{
        return state.pages.firstOrNull() { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
            remoteKeysDao.getRemoteKeysBy(it.id)
        }
    }

    override suspend fun initialize(): InitializeAction {
       val isSame =  withContext(Dispatchers.IO) {
            val remoteDeferred = async { articleService.getArticle(0, 2).data.datas.firstOrNull() }
            val localDeferred = async { articleDao.firstOrNullArticle() }
            val remoteFirstArticle =  remoteDeferred.await()
            val localFirstArticle =  localDeferred.await()
            return@withContext remoteFirstArticle != null && localFirstArticle != null  && remoteFirstArticle.id == localFirstArticle.id
        }
        Log.e(TAG, "initialize: isSame $isSame", )
        return  if (isSame) InitializeAction.SKIP_INITIAL_REFRESH else InitializeAction.LAUNCH_INITIAL_REFRESH
    }
}