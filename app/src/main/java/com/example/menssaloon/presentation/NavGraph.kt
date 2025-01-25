package com.example.menssaloon.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.menssaloon.presentation.admin.appointment.AppointmentScreen
import com.example.menssaloon.presentation.admin.dashboard.DashboardScreen
import com.example.menssaloon.presentation.admin.service_screen.ServiceListScreen
import com.example.menssaloon.presentation.admin.barber_screen.BarberListScreen
import com.example.menssaloon.presentation.user.welcome_page.WelcomePage

@Composable
fun NavGraphSetup(
    navHostController: NavHostController,
   ) {

    NavHost(navHostController, startDestination = "WelcomePage") {
        composable("WelcomePage"){
            WelcomePage(
                onLoginClick = {navHostController.navigate("DashboardScreen")},
                onBookingClicked = {navHostController.navigateUp()}
            )

        }
        composable("DashboardScreen"){
            DashboardScreen(
                onBarbersClick = {navHostController.navigate("BarberScreen")},
                onEditServicesClick = {
                    navHostController.navigate("AddEditServiceScreen")
                },
                onAppointmentClick = {
                    navHostController.navigate("AppointmentScreen")
                },
                onSignOutClick = {navHostController.navigateUp()}
            )
        }
        composable("AppointmentScreen"){

            AppointmentScreen(
                onBarbersClick = {navHostController.navigate("BarberScreen")},
                onEditServicesClick = {
                    navHostController.navigate("AddEditServiceScreen")
                },
                onDropClick = {},
                onBackClick = {navHostController.navigateUp()}
            )

        }
        composable("BarberScreen"){
            BarberListScreen(
                onBackClick = {
                    navHostController.navigateUp()
                }
            )
        }
        composable("AddEditServiceScreen"){
            ServiceListScreen(
                onBackClick = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}