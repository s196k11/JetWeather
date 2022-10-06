package com.example.jetweather.screens.Setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.jetweather.widgets.WeatherAppBar

@Composable
fun SettingsScreen(navController: NavController) {


    Scaffold(topBar = {
        WeatherAppBar(
            title = "Settings",
            navController = navController,
            icon = Icons.Default.ArrowBack,
            isMainScreen = false,
        ) {
            navController.popBackStack()
        }
    }) {

        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                Text("Change Unites of Measurement")
                Text("By")
                Text(text = "Clicking on Temperature at Home Screen")


            }
        }

    }
}