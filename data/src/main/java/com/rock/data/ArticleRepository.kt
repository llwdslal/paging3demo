package com.rock.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.rock.data.remote.ArticlePagingSource
import com.rock.data.remote.ArticleRemoteMediator
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject



@ViewModelScoped
class ArticleRepository @Inject constructor(
    private val pagingSource: ArticlePagingSource,
    private val remoteMediator: ArticleRemoteMediator,
    private val pagingConfig: PagingConfig
){


    val articlePagerFlow = Pager(pagingConfig){ pagingSource }.flow

    @OptIn(ExperimentalPagingApi::class)
    val articleRemoteMediatorPagerFlow = Pager(
        config = pagingConfig,
        remoteMediator = remoteMediator
    ){
        remoteMediator.articleDao.pagingSource()
    }.flow
}