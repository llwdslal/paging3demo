package com.rock.paging3demo

import android.os.Bundle
import android.text.Html
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.text.htmlEncode
import androidx.lifecycle.ViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.rock.data.entity.Article
import com.rock.paging3demo.ui.theme.Paging3demoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = viewModels<ArticlePagingVM>().value

        setContent {
            Paging3demoTheme {
                ArticlePagingList(flow = viewModel.articleRemoteMediatorFlow)
            }
        }
    }
}


@Composable
fun ArticlePagingList(flow: Flow<PagingData<Article>>){
    val lazyPagingItem = flow.collectAsLazyPagingItems()
    LazyColumn(contentPadding = PaddingValues(vertical = 80.dp)){

        items(lazyPagingItem){ article ->
            if (article != null){
                Text(modifier = Modifier.padding(12.dp),
                    text = "${Html.fromHtml(article.title,Html.FROM_HTML_MODE_LEGACY)}")
            }else{
                Holder()
            }
        }

        if (lazyPagingItem.loadState.append == LoadState.Loading) {
            item {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@Composable
fun Holder(){
    Text(modifier = Modifier.padding(12.dp), text = "")
}
