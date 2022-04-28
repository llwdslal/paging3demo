package com.rock.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.rock.data.remote.ArticlePagingSource
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject



@ViewModelScoped
class ArticleRepository @Inject constructor(
    private val pagingSource: ArticlePagingSource,
    private val pagingConfig: PagingConfig
){


    val articlePagerFlow = Pager(pagingConfig){ pagingSource }.flow
}