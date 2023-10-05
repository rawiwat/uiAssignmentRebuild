 package com.example.uiassignment

 import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
 import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uiassignment.composeui.HomeScreen
import com.example.uiassignment.composeui.InfoScreen
import com.example.uiassignment.composeui.NFTDetail
import com.example.uiassignment.composeui.QrCodeScanner
import com.example.uiassignment.composeui.SettingScreen
import com.example.uiassignment.composeui.Archive
 import com.example.uiassignment.composeui.SwapScreen
 import com.example.uiassignment.composeui.Transaction
import com.example.uiassignment.ui.theme.UiAssignmentTheme
 import com.example.uiassignment.viewmodel.ArchiveViewModel
 import com.example.uiassignment.viewmodel.HomeViewModel
 import com.example.uiassignment.viewmodel.InfoScreenViewModel
 import com.example.uiassignment.viewmodel.NFTDetailViewModel
 import com.example.uiassignment.viewmodel.SwapViewModel
 import com.example.uiassignment.viewmodel.TransactionViewModel

 class MainActivity : ComponentActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
        super.onCreate(savedInstanceState)
        val selectedDatabase:Database = FakeDatabase()
        setContent {
            UiAssignmentTheme {
                navController = rememberNavController()
                App(
                    navController as NavHostController,
                    this@MainActivity,
                    selectedDatabase
                )
            }
        }
    }
}


 @Composable
 fun App(
     navController: NavHostController,
     context: Context,
     selectedDatabase: Database
 ) {
     val animationTime = 1200
     NavHost(
         navController = navController,
         startDestination = "Home"
     ) {
         composable(
             route = "Home",
             enterTransition = {
                 slideIntoContainer(
                     AnimatedContentTransitionScope.SlideDirection.Right,
                     animationSpec = tween(animationTime)
                 )
             },
             exitTransition = {
                 slideOutOfContainer(
                     AnimatedContentTransitionScope.SlideDirection.Left,
                     animationSpec = tween(animationTime)
                 )
             }
         ) {
             HomeScreen(
                 navController = navController,
                 HomeViewModel(selectedDatabase)
             )
         }

         composable(
             route = "InfoScreen/{id}",
             arguments = listOf(
                 navArgument(name = "id") {
                     type = NavType.IntType
                 }
             ),
             enterTransition = {
                 slideIntoContainer(
                     AnimatedContentTransitionScope.SlideDirection.Left,
                     animationSpec = tween(animationTime)
                 )
             },
             exitTransition = {
                 slideOutOfContainer(
                     AnimatedContentTransitionScope.SlideDirection.Right,
                     animationSpec = tween(animationTime)
                 )
             }
         ) {
             InfoScreen(
                 navController,
                 InfoScreenViewModel(
                     selectedDatabase,
                     it.arguments!!.getInt("id")
                 )
             )
         }

         composable(
             route = "transaction/{id}",
             arguments = listOf(
                 navArgument(name = "id") {
                     type = NavType.IntType
                 }
             ),
             enterTransition = {
                 slideIntoContainer(
                     AnimatedContentTransitionScope.SlideDirection.Up,
                     animationSpec = tween(animationTime)
                 )
             },
             exitTransition = {
                 slideOutOfContainer(
                     AnimatedContentTransitionScope.SlideDirection.Down,
                     animationSpec = tween(animationTime)
                 )
             }
         ) {
             it.arguments?.let { it1 ->
                 Transaction(TransactionViewModel(selectedDatabase,it1.getInt("id")))
             }
         }

         composable(
             route = "Archive/{id}",
             arguments = listOf(
                 navArgument(name = "id") {
                     type = NavType.IntType
                 }
             )
         ) {
             if (it.arguments!=null) {
                 Archive(
                     navController = navController,
                     archiveViewModel = ArchiveViewModel(selectedDatabase,it.arguments!!.getInt("id"))
                 )
             }
         }

         composable(
             route = "PhotoDetail/{userId}/{photoId}",
             arguments = listOf(
                 navArgument(name = "modelId") {
                     type = NavType.IntType
                 },
                 navArgument(name = "nftId") {
                     type = NavType.IntType
                 }
             )
         ) {
             if (it.arguments!=null) {
                 NFTDetail(
                     navController = navController,
                     nftDetailViewModel = NFTDetailViewModel(
                         selectedDatabase,
                         nftId = it.arguments!!.getInt("nftId"),
                         modelId = it.arguments!!.getInt("modelId")
                     )
                 )
             }
         }

         composable(route = "Setting") {
             SettingScreen(navController = navController)
         }

         composable(route = "QR Code") {
             QrCodeScanner(context = context)
         }

         composable(route = "Swap/{id}",
             arguments = listOf(
                 navArgument(name = "id") {
                     type = NavType.IntType
                 }
             )
         ) {
             if (it.arguments!=null) {
                 SwapScreen(swapViewModel = SwapViewModel(selectedDatabase,it.arguments!!.getInt("id")))
             }
         }
     }
 }

