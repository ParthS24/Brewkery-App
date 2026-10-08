package com.example.brewkeryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brewkeryapp.data.api.BrewkeryApi
import com.example.brewkeryapp.data.models.CartItem
import com.example.brewkeryapp.data.models.MenuItem
import com.example.brewkeryapp.data.models.MenuResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UiState(
    val isLoading: Boolean = true,
    val menuData: MenuResponse? = null,
    val error: String? = null,
    val cart: List<CartItem> = emptyList(),
    val activeOrder: String? = null,
    val selectedCategoryId: String? = null
)

class BrewkeryViewModel : ViewModel() {
    private val api = BrewkeryApi.create()

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        loadMenu()
    }

    private fun loadMenu() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val menu = api.getMenu()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    menuData = menu
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load menu"
                )
            }
        }
    }

    fun addToCart(menuItem: MenuItem, sizeId: String, milkId: String, sugarLevel: String) {
        val size = menuItem.customizations.sizes.find { it.id == sizeId }
            ?: menuItem.customizations.sizes.first()
        val milk = menuItem.customizations.milkOptions.find { it.id == milkId }
            ?: menuItem.customizations.milkOptions.first()

        val existingItem = _uiState.value.cart.find {
            it.menuItem.id == menuItem.id &&
            it.selectedSize.id == sizeId &&
            it.selectedMilk.id == milkId &&
            it.selectedSugar == sugarLevel
        }

        val updatedCart = if (existingItem != null) {
            _uiState.value.cart.map {
                if (it == existingItem) {
                    it.copy(quantity = it.quantity + 1)
                } else {
                    it
                }
            }
        } else {
            _uiState.value.cart + CartItem(
                menuItem = menuItem,
                selectedSize = size,
                selectedMilk = milk,
                selectedSugar = sugarLevel,
                quantity = 1
            )
        }

        _uiState.value = _uiState.value.copy(cart = updatedCart)
    }

    fun removeFromCart(cartItem: CartItem) {
        val updatedCart = _uiState.value.cart.filter { it != cartItem }
        _uiState.value = _uiState.value.copy(cart = updatedCart)
    }

    fun updateQuantity(cartItem: CartItem, delta: Int) {
        val updatedCart = _uiState.value.cart.map {
            if (it == cartItem) {
                val newQuantity = it.quantity + delta
                if (newQuantity > 0) {
                    it.copy(quantity = newQuantity)
                } else {
                    it
                }
            } else {
                it
            }
        }
        _uiState.value = _uiState.value.copy(cart = updatedCart)
    }

    fun clearCart() {
        _uiState.value = _uiState.value.copy(cart = emptyList())
    }

    fun placeOrder(): String {
        val ticketId = "BK-${(10000..99999).random()}"
        _uiState.value = _uiState.value.copy(
            cart = emptyList(),
            activeOrder = ticketId
        )
        return ticketId
    }

    fun clearOrder() {
        _uiState.value = _uiState.value.copy(activeOrder = null)
    }

    fun getCartSubtotal(): Double {
        return _uiState.value.cart.sumOf { it.totalPrice }
    }

    fun getCartTotal(): Double {
        val subtotal = getCartSubtotal()
        val deliveryFee = _uiState.value.menuData?.meta?.deliveryFee ?: 0.0
        val taxRate = _uiState.value.menuData?.meta?.taxRatePercent ?: 0.0
        val tax = subtotal * (taxRate / 100)
        return subtotal + deliveryFee + tax
    }

    fun selectCategory(categoryId: String?) {
        _uiState.value = _uiState.value.copy(selectedCategoryId = categoryId)
    }

    fun getFilteredItems(): List<MenuItem> {
        val allItems = _uiState.value.menuData?.items ?: emptyList()
        val selectedCategoryId = _uiState.value.selectedCategoryId
        return if (selectedCategoryId != null) {
            allItems.filter { it.categoryId == selectedCategoryId }
        } else {
            allItems
        }
    }
}
