package com.example.shoppinglistcomposelesson.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglistcomposelesson.about_screen.AboutScreen
import com.example.shoppinglistcomposelesson.add_item_screen.AddItemScreen
import com.example.shoppinglistcomposelesson.main_screen.MainScreen
import com.example.shoppinglistcomposelesson.new_note_screen.NewNoteScreen
import com.example.shoppinglistcomposelesson.note_list_screen.NoteListScreen
import com.example.shoppinglistcomposelesson.settings_screen.SettingsScreen
import com.example.shoppinglistcomposelesson.shopping_list_screen.ShoppingListScreen
import com.example.shoppinglistcomposelesson.utils.Routes

@Composable
fun MainNavigationGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination =  Routes.MAIN_SCREEN){
        composable(Routes.ADD_ITEM + "/{listId}") {
            AddItemScreen()
        }
        composable(Routes.NEW_NOTE) {
            NewNoteScreen()
        }
        composable(Routes.MAIN_SCREEN) {
            MainScreen(navController)
        }
    }
}
