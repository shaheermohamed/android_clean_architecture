package com.shahe.basiclearning.presentation.news_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.shahe.basiclearning.common.AnimatedPreloader
import com.shahe.basiclearning.domain.model.Article
import com.shahe.basiclearning.presentation.components.BottomNavigationBar
import com.shahe.basiclearning.presentation.components.TopAppBar

@Composable
fun NewsListScreen(
    navController: NavController,
    viewModel: NewsListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val newsCategories = listOf(
        "general", "world", "nation", "business",
        "technology", "entertainment", "sports", "science", "health"
    )
    val selectedCategory = viewModel.selectedCategory.value

    Scaffold(
        topBar = {
            TopAppBar(navController)
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                CategorySelector(
                    categories = newsCategories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = viewModel::onCategorySelected
                )
                Spacer(Modifier.height(10.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(state.news?.articles ?: emptyList<Article>()) { article ->
                        NewsItem(article)
                        Divider()
                    }
                }
            }
            if (state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.Center)
                )
            }

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    AnimatedPreloader(
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun NewsItem(article: Article) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        // Show article image
        AsyncImage(
            model = article.image,
            contentDescription = article.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = article.title, style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = article.description,
            style = MaterialTheme.typography.body2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun CategorySelector(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items(categories) { category ->
            val isSelected = selectedCategory == category
            Button(
                onClick = { onCategorySelected(category) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (isSelected) MaterialTheme.colors.primary else Color.LightGray,
                    contentColor = if (isSelected) Color.White else Color.Black
                ),
                modifier = Modifier
                    .padding(end = 8.dp)
            ) {
                Text(text = category.capitalize())
            }
        }
    }
}