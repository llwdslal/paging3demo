package com.rock.paging3demo

import android.os.Bundle
import android.text.Html
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.text.htmlEncode
import androidx.lifecycle.ViewModel
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
                ArticlePagingList(flow = viewModel.articleFlow)
            }
        }
    }
}


@Composable
fun ArticlePagingList(flow: Flow<PagingData<Article>>){
    val lazyPagingItem = flow.collectAsLazyPagingItems()
    LazyColumn{

        items(lazyPagingItem){ article ->
            article?.let {
                Text(modifier = Modifier.padding(12.dp),
                    text = "${Html.fromHtml(it.title,Html.FROM_HTML_MODE_LEGACY)}")
            }
        }
    }
}
