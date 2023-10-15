package com.gustavo.omiepdv.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gustavo.omiepdv.presentation.screens.Screens
import com.gustavo.omiepdv.presentation.screens.history.HistoryScreen
import com.gustavo.omiepdv.presentation.screens.home.HomeFeatures
import com.gustavo.omiepdv.presentation.screens.home.HomeScreen
import com.gustavo.omiepdv.presentation.screens.order.NewOrderScreen
import com.gustavo.omiepdv.presentation.screens.product.NewProductScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screens.HOME.route) {
        composable(Screens.HOME.route) {
            HomeScreen(
                onNavigateToFeature = {
                    when (it) {
                        HomeFeatures.HISTORY -> navController.navigate(Screens.HISTORY.route)
                        HomeFeatures.NEW_PRODUCT -> navController.navigate(Screens.NEW_PRODUCT.route)
                        HomeFeatures.NEW_SALE -> navController.navigate(Screens.NEW_ORDER.route)
                    }
                }
            )
        }

        composable(Screens.NEW_ORDER.route) {
            NewOrderScreen(
                onAddProduct = { navController.navigate(Screens.NEW_PRODUCT.route) },
                navigateBack = navController::popBackStack
            )
        }

        composable(Screens.NEW_PRODUCT.route) {
            NewProductScreen(navigateBack = navController::popBackStack)
        }

        composable(Screens.HISTORY.route) {
            HistoryScreen(onBackPressed = navController::popBackStack)
        }
    }
}