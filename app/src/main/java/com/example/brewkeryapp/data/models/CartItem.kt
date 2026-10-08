package com.example.brewkeryapp.data.models

data class CartItem(
    val menuItem: MenuItem,
    val selectedSize: SizeOption,
    val selectedMilk: MilkOption,
    val selectedSugar: String,
    val quantity: Int = 1
) {
    val totalPrice: Double
        get() = (menuItem.basePrice + selectedSize.extraPrice + selectedMilk.extraPrice) * quantity
}
