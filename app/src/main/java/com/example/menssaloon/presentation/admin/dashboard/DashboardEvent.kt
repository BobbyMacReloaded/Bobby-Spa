package com.example.menssaloon.presentation.admin.dashboard

sealed class DashboardEvent {
    data object IncrementServiceCount : DashboardEvent()
    data object DecrementServiceCount : DashboardEvent()
    data object IncrementBarbersCount : DashboardEvent()
    data object DecrementBarbersCount : DashboardEvent()
    data object IncrementPendingRequests : DashboardEvent()
    data object DecrementPendingRequests : DashboardEvent()
}