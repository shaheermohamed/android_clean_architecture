package com.shahe.basiclearning.presentation.advice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shahe.basiclearning.common.AnimatedPreloader

@Composable
fun AdviceScreen(viewModel: AdviceViewModel = hiltViewModel()) {
    val state = viewModel.state
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        if (state?.error?.isNotBlank() == true) {
            Text(
                text = state.error.toString(),
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
        if (state.isLoading == true) Box(modifier = Modifier.fillMaxWidth()) {
            AnimatedPreloader(modifier = Modifier
                .size(80.dp)
                .align(Alignment.Center))
        }
        state.let {
            if (it.advice?.advice?.isNotEmpty() == true) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp, horizontal = 4.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(2.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colors.background)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Advice id: ${it.advice.id}",
                            style = MaterialTheme.typography.subtitle2
                        )
                        Text(
                            text = it.advice.advice,
                            style = MaterialTheme.typography.subtitle2
                        )
                        Divider()
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            modifier = Modifier.align(alignment = Alignment.End),
                            onClick = { viewModel.fetchAdvice() }) {
                            Text(text = "Refresh")
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Search",
                                tint = Color.White
                            )
                        }
                    }
                }
            }


        }
    }
}