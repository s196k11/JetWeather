package com.example.jetweather.screens.Favorite


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweather.WeatherApp
import com.example.jetweather.model.Favorite
import com.example.jetweather.navigation.WeatherScreens
import com.example.jetweather.widgets.WeatherAppBar

@Composable
fun FavoriteScreen(
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = "Favorite",
                navController = navController,
                icon = Icons.Default.ArrowBack,
                isMainScreen = false,
            ) {
                navController.popBackStack()
            }

        }
    ) {
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(it)
        ) {

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val list = favoriteViewModel.favList.collectAsState().value

                LazyColumn {
                    items(items = list) {
                        CityRow(it, navController = navController, favoriteViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun CityRow(
    favorite: Favorite,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel,
) {

    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(3.dp)
        .height(50.dp)
        .clickable {
//                   navController.navigate(WeatherScreens.MainScreen.name + "/${favorite.city}")
        },
        shape = CircleShape.copy(topEnd = CornerSize(7.dp)),
        color = Color(0xffb2dfdb)
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {

            Text(text = favorite.city,modifier = Modifier.padding(start = 4.dp))

            Surface(modifier = Modifier.height(25.dp).width(45.dp),shape = CircleShape) {

                Text(text = favorite.country, overflow = TextOverflow.Ellipsis)
            }

            Icon(imageVector = Icons.Default.Delete, contentDescription = null,
                modifier = Modifier.clickable {
                    favoriteViewModel.deleteFavorite(favorite)
                    navController.navigate(WeatherScreens.MainScreen.name + "/${favorite.city}")
                },
                tint = Color.Red.copy(alpha = 0.3f)
            )
        }
    }
}
