package com.android.dailyrounds.view.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.dailyrounds.view.book.BookScreen

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(navController = navController, route = Graph.ROOT, startDestination = Screen.BOOK)
    {
        composable(route = Screen.BOOK) {
            BookScreen()
        }
    }
}


object Screen {
    const val BOOK = "book_screen"
}

object Graph {
    const val ROOT = "root_graph"
}