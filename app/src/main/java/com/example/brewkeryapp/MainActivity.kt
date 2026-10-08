package com.example.brewkeryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.brewkeryapp.ui.navigation.Screen
import com.example.brewkeryapp.ui.screens.CartScreen
import com.example.brewkeryapp.ui.screens.ItemDetailScreen
import com.example.brewkeryapp.ui.screens.MenuScreen
import com.example.brewkeryapp.ui.screens.OrderStatusScreen
import com.example.brewkeryapp.ui.theme.BrewkeryAppTheme
import com.example.brewkeryapp.viewmodel.BrewkeryViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BrewkeryAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BrewkeryApp()
                }
            }
        }
    }
}

@Composable
fun BrewkeryApp(viewModel: BrewkeryViewModel = viewModel()) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.Menu.route
    ) {
        composable(Screen.Menu.route) {
            MenuScreen(
                viewModel = viewModel,
                onItemClick = { item ->
                    navController.navigate(Screen.ItemDetail.createRoute(item.id))
                },
                onCartClick = {
                    navController.navigate(Screen.Cart.route)
                },
                onRetry = {
                    // Reload the menu
                }
            )
        }

        composable(
            route = Screen.ItemDetail.route,
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId") ?: 1
            val item = uiState.menuData?.items?.find { it.id == itemId }
            item?.let {
                ItemDetailScreen(
                    item = it,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onAddToCart = { navController.navigate(Screen.Cart.route) }
                )
            }
        }

        composable(Screen.Cart.route) {
            CartScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onPlaceOrder = { ticketId ->
                    navController.navigate(Screen.OrderStatus.createRoute(ticketId)) {
                        popUpTo(Screen.Menu.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.OrderStatus.route,
            arguments = listOf(navArgument("ticketId") { type = NavType.StringType })
        ) {
            OrderStatusScreen(
                viewModel = viewModel,
                onBackToMenu = {
                    navController.navigate(Screen.Menu.route) {
                        popUpTo(Screen.Menu.route) { inclusive = true }
                    }
                }
            )
        }
    }
}