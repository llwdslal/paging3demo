package com.rock.paging3demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.rock.data.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject




@HiltViewModel
class ArticlePagingVM @Inject constructor(
    private val articleRepository: ArticleRepository
):ViewModel(){
    val articleFlow = articleRepository.articlePagerFlow.cachedIn(viewModelScope)



}