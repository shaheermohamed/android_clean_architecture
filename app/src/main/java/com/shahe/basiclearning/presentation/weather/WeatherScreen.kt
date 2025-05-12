package com.shahe.basiclearning.presentation.weather

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.primarySurface
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.shahe.basiclearning.common.LocationHelper
import com.shahe.basiclearning.presentation.components.BottomNavigationBar
import com.shahe.basiclearning.presentation.components.TopAppBar

@Composable
fun WeatherScreen(
    navController: NavController,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val state = viewModel.state
    var selectedCity by remember { mutableStateOf("") }
    val context = LocalContext.current
    val cities = listOf(
        "Abu Dhabi",
        "Dubai",
        "Sharjah",
        "Ajman",
        "Umm Al Quwain",
        "Ras Al Khaimah",
        "Fujairah", "New York", "London", "Paris", "Tokyo", "Delhi",
        "Sydney", "Cairo", "Toronto", "Moscow"
    )
    var selectedText by remember { mutableStateOf("") }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                LocationHelper.getCityNameFromLocation(context) { city ->
                    city?.let {
                        viewModel.fetchWeather(it)
                    } ?: Toast.makeText(context, "City not found", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    )
    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
    Scaffold(
        topBar = { TopAppBar(navController) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                CitySelector(cityList = cities, { city ->
                    selectedCity = city
                }, selectedText, { selectedText = it }, viewModel)
//                Button(onClick = {
//                    if (selectedCity.isNotEmpty()) {
//                        viewModel.fetchWeather(selectedCity)
//                        selectedText = ""
//                    } else {
//                        Toast.makeText(context, "Enter Any City", Toast.LENGTH_SHORT).show()
//                    }
//                }, modifier = Modifier.padding(start = 2.dp)) {
//                    Text("Get Weather")
//                }
            }

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
            if (state?.isLoading == true) CircularProgressIndicator()
            state?.let {
                if (it.weather?.city?.isNotEmpty() == true && state.isLoading == false) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp, horizontal = 4.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colors.primarySurface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "📍 City: ${it.weather.city}",
                                style = MaterialTheme.typography.h6,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "🌡️ Temp: ${
                                    String.format(
                                        "%.1f",
                                        it.weather?.temperature
                                    )
                                }°C",
                                style = MaterialTheme.typography.body1,
                                color = Color.White
                            )

                            Text(
                                text = "🥵 Feels Like: ${
                                    String.format(
                                        "%.1f",
                                        it.weather?.temperatureFeelsLike
                                    )
                                }°C",
                                style = MaterialTheme.typography.body1,
                                color = Color.White
                            )

                            Text(
                                text = "🌬️ Wind: ${String.format("%.1f", it.weather?.wind)} m/s",
                                style = MaterialTheme.typography.body1,
                                color = Color.White
                            )

                            Text(
                                text = "☁️ Desc: ${it.weather?.description}",
                                style = MaterialTheme.typography.body2,
                                color = Color.White
                            )

                            Text(
                                text = "🧭 Coordinates: (${it.weather?.coordinates?.lat}, ${it.weather?.coordinates?.lon})",
                                style = MaterialTheme.typography.caption,
                                color = Color.White
                            )
                        }
                    }
                }


            }
        }
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CitySelector(
    cityList: List<String>,
    onCitySelected: (String) -> Unit,
    selectedText: String,
    onSelectedTextChange: (String) -> Unit,
    viewModel: WeatherViewModel
) {
    var expanded by remember { mutableStateOf(false) }

    val filteredOptions = remember(selectedText) {
        if (selectedText.isBlank()) cityList
        else cityList.filter {
            it.contains(selectedText, ignoreCase = true)
        }
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier
            .border(
                border = BorderStroke(2.dp, color = Color.DarkGray),
                shape = RoundedCornerShape(16.dp)
            )
            .background(color = Color.White)
    ) {
        TextField(
            value = selectedText,
            onValueChange = {
                onSelectedTextChange(it)
                expanded = true
            },
            label = { Text("Select a city") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
//            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            filteredOptions.forEach { city ->
                DropdownMenuItem(
                    text = { Text(city) },
                    onClick = {
                        onSelectedTextChange(city)
                        expanded = false
                        onCitySelected(city)
                        viewModel.fetchWeather(city)
                        onSelectedTextChange("")
                    }
                )
            }
        }
    }
}
