package com.example.nammahomestay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.nammahomestay.ui.theme.NammaHomeStayTheme

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NammaHomeStayTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") { HomeScreen(onNavigate = { navController.navigate(it) }) }
                        composable("profile") { HomeScreen(onNavigate = { navController.navigate(it) }) }
                        composable("menu") { HomeScreen(onNavigate = { navController.navigate(it) }) }
                        composable("inquiries") { HomeScreen(onNavigate = { navController.navigate(it) }) }
                        composable("guide") { HomeScreen(onNavigate = { navController.navigate(it) }) }
                    }
                }
            }
        }
    }
}


