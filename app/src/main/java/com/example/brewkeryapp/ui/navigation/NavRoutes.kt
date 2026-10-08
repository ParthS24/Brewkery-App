package com.example.brewkeryapp.ui.navigation

sealed class Screen(val route: String) {
    object Menu : Screen("menu")
    object ItemDetail : Screen("item_detail/{itemId}") {
        fun createRoute(itemId: Int) = "item_detail/$itemId"
    }
    object Cart : Screen("cart")
    object OrderStatus : Screen("order_status/{ticketId}") {
        fun createRoute(ticketId: String) = "order_status/$ticketId"
    }
}
