package com.rock.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rock.data.entity.Article
import com.rock.lib_base.net.handleResponse
import dagger.hilt.android.scopes.ViewModelScoped
import java.util.concurrent.CancellationException
import javax.inject.Inject

@ViewModelScoped
class ArticlePagingSource @Inject constructor(
    private val service: WanAndroidService
) :PagingSource<Int,Article>(){

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val nextPage = params.key ?: 0
            val response = service.getArticle(nextPage,params.loadSize)

            response.handleResponse { pagedData ->
                if (pagedData.over){
                    LoadResult.Page(data = pagedData.datas, prevKey = null, nextKey = null)
                }else{
                    LoadResult.Page(data = pagedData.datas, prevKey = null, nextKey = nextPage.plus(1))
                }

            }
        }catch (e:Throwable){
            if (e is CancellationException){
                throw e
            }
            LoadResult.Error(e)
        }
    }

}