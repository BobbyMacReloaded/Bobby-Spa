package com.example.menssaloon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.menssaloon.presentation.NavGraphSetup
import com.example.menssaloon.presentation.theme.MensSaloonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MensSaloonTheme {
              NavGraphSetup(
                  navHostController = rememberNavController()
              )
            }
        }
    }
}

