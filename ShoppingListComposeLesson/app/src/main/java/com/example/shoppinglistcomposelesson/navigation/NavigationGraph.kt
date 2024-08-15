package com.example.shoppinglistcomposelesson.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglistcomposelesson.about_screen.AboutScreen
import com.example.shoppinglistcomposelesson.note_list_screen.NoteListScreen
import com.example.shoppinglistcomposelesson.settings_screen.SettingsScreen
import com.example.shoppinglistcomposelesson.shopping_list_screen.ShoppingListScreen
import com.example.shoppinglistcomposelesson.utils.Routes
import com.example.shoppinglistcomposelesson.utils.UiEvent

@Composable
fun NavigationGraph(navController: NavHostController, onNavigate: (String) -> Unit) {

    NavHost(navController = navController, startDestination =  Routes.SHOPPING_LIST){
        composable(Routes.SHOPPING_LIST) {
            ShoppingListScreen(){ route ->
                onNavigate(route)
            }
        }
        composable(Routes.NOTE_LIST) {
            NoteListScreen()
        }
        composable(Routes.ABOUT) {
            AboutScreen()
        }
        composable(Routes.SETTINGS) {
            SettingsScreen()
        }
    }
}
