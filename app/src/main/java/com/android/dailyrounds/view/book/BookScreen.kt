package com.android.dailyrounds.view.book

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ahmadhamwi.tabsync_compose.lazyListTabSync
import com.android.dailyrounds.model.Book
import com.android.dailyrounds.viewmodel.BookState
import com.android.dailyrounds.viewmodel.BookViewModel

@Composable
fun BookScreen(
    viewModel: BookViewModel = hiltViewModel(),
) {
    val state = viewModel.bookState.value
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BookListView(state, viewModel.books)
    }
}

@Composable
fun BookListView(dataItems: BookState, books: List<Book>?) {
    if (dataItems.isLoading) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    } else {
        dataItems.books?.let { map ->

            val index = arrayListOf<Int>()
            map.forEach { (i, data) ->
                index.add(books?.indexOf(data.first()) ?: 0)
            }
            val (selectedTabIndex, setSelectedTabIndex, lazyListState) = lazyListTabSync(
                index,
                smoothScroll = true,
                lazyListState = rememberLazyListState(),
                tabsCount = map.keys.size
            )

            Column {
                ScrollableTabRow(selectedTabIndex) {
                    map.keys.forEachIndexed { index, key ->
                        Tab(selected = index == selectedTabIndex,
                            onClick = { setSelectedTabIndex(index) },
                            text = { Text(text = key.toString()) })
                    }
                }

                LazyColumn(state = lazyListState) {
                    map.forEach {
                        item {
                            Text(
                                text = it.key.toString(),
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                        items(it.value, key = { it.id }) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(vertical = 4.dp)
                            ) {
                                AsyncImage(
                                    model = it.image,
                                    contentDescription = it.title,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                )
                                Text(text = it.title)
                            }
                        }
                    }
                }
            }
        }
    }
}