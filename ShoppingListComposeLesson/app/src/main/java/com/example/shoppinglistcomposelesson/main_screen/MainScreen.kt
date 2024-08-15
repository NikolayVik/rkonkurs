package com.example.shoppinglistcomposelesson.main_screen

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarDefaults.containerColor
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglistcomposelesson.R
import com.example.shoppinglistcomposelesson.dialog.MainDialog
import com.example.shoppinglistcomposelesson.navigation.NavigationGraph
import com.example.shoppinglistcomposelesson.shopping_list_screen.ShoppingListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    mainNavHostController: NavHostController,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNav(navController)
    },
    floatingActionButton = {
        FloatingActionButton(
            onClick = {
            viewModel.onEvent(MainScreenEvent.OnShowEditDialog)
            } ) {
            Icon(painter = painterResource(
                id = R.drawable.add_icon),
                contentDescription = "Add",
                tint = Color.White
                )

        }
    },
        floatingActionButtonPosition = FabPosition.Center

    ) {
        NavigationGraph(navController){ route ->
            mainNavHostController.navigate(route)
        }
        MainDialog(viewModel)
    }
}
