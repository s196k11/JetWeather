package com.example.jetweather.screens.main

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.jetweather.R
import com.example.jetweather.data.DataOrException
import com.example.jetweather.model.Weather
import com.example.jetweather.navigation.WeatherScreens
import com.example.jetweather.widgets.WeatherAppBar


@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    city: String?,
) {
    Log.d("TAG","MainScreen: $city")
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)) {
        value = mainViewModel.getWeatherData(city = city.toString())
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weather = weatherData.data!!, navController)
    }
}


@Composable
fun MainScaffold(weather: Weather, navController: NavController) {

    Scaffold(
        topBar = {
            WeatherAppBar(
                title = weather.location.region + ",${weather.location.country}",
                navController = navController,
                onAddActionClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name)
                },
                elevation = 5.dp,
            ) {
                Log.d("Button MainScaff", "MainScaffold: Button Clicked")
            }
        }
    ) {
        MainContent(data = weather, padding = it)
    }

}

@Composable
fun MainContent(data: Weather, padding: Any) {

    val temp = remember {mutableStateOf("°C")}

    Column(modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth()
        .verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = data.location.localtime.substring(10),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(4.dp)
        )

        Surface(modifier = Modifier
            .padding(4.dp)
            .size(200.dp),
            shape = RoundedCornerShape(75.dp),
            color = Color(0xffFFC400)
        ) {
            Column(verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(model = "https:" + data.current.condition.icon,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp))

                Log.d("Image: ", data.current.condition.icon)


                if (temp.value == "°C"){
                    Text(text = "%.0f".format(data.current.temp_c) + temp.value,
                        style = MaterialTheme.typography.h4,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.clickable {
                            if (temp.value == "°C"){
                                temp.value = "°F"
                            }else{
                                if (temp.value == "°F"){
                                    temp.value = "°C"
                                }
                            }
                        }
                    )
                }else{
                    Text(text = "%.0f".format(data.current.temp_f) + temp.value,
                        style = MaterialTheme.typography.h4,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.clickable {
                            if (temp.value == "°C"){
                                temp.value = "°F"
                            }else{
                                if (temp.value == "°F"){
                                    temp.value = "°C"
                                }
                            }
                        }
                    )
                }


                Text(text = data.current.condition.text, fontStyle = FontStyle.Italic)
            }
        }
        HumidityWindPressureRow(weather = data)
        Divider()
    }


}

@Composable
fun HumidityWindPressureRow(weather: Weather) {
    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity icon",
                modifier = Modifier.size(20.dp)
            )

            Text(text = weather.current.humidity.toString() + "%",
                style = MaterialTheme.typography.caption)
        }

        Row() {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure icon",
                modifier = Modifier.size(20.dp)
            )

            Text(text = weather.current.pressure_in.toString() + " psi",
                style = MaterialTheme.typography.caption)
        }

        Row() {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind icon",
                modifier = Modifier.size(20.dp)
            )

            Text(text = weather.current.wind_kph.toString() + " kph",
                style = MaterialTheme.typography.caption)
        }
    }
}


