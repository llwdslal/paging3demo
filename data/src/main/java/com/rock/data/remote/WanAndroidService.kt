package com.rock.data.remote

import com.rock.data.entity.Article
import com.rock.data.entity.Banner
import com.rock.data.entity.remote.PagedData
import com.rock.lib_base.net.NetResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WanAndroidService {
    companion object{
       const val baseUrlDebug = "https://www.wanandroid.com"
       const val baseUrlPreProduct = "https://www.wanandroid.com"
       const val baseUrlProduct = "https://www.wanandroid.com"
    }
    @GET("article/top/json")
    suspend fun getTopArticles(): NetResponse<List<Article>>

    @GET("banner/json")
    suspend fun getBanners(): NetResponse<List<Banner>>

    @GET("https://www.wanandroid.com/article/list/{page}/json")
    suspend fun getArticle(@Path("page") page:Int, @Query("page_size") pageSize:Int):NetResponse<PagedData<Article>>

}

