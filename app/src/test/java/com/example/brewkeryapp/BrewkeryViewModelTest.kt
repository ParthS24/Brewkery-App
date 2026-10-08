package com.example.brewkeryapp

import com.example.brewkeryapp.data.models.*
import org.junit.Assert.*
import org.junit.Test

class BrewkeryViewModelTest {

    private val testMenuItem = MenuItem(
        id = 1,
        categoryId = "cat_hot_coffee",
        name = "Test Coffee",
        tagline = "Test tagline",
        description = "Test description",
        basePrice = 4.85,
        rating = 4.9,
        reviewCount = 100,
        prepTime = "5 mins",
        calories = 200,
        imageUrl = "https://test.com/image.jpg",
        badge = "TEST",
        ingredients = listOf("Coffee", "Milk"),
        customizations = Customizations(
            sizes = listOf(
                SizeOption("sz_small", "Small", 0.0),
                SizeOption("sz_medium", "Medium", 0.65)
            ),
            sugarLevels = listOf("Regular", "Less"),
            milkOptions = listOf(
                MilkOption("m_oat", "Oat Milk", 0.0),
                MilkOption("m_almond", "Almond Milk", 0.50)
            )
        )
    )

    @Test
    fun testCartItemTotalPrice_calculation() {
        val sizeOption = testMenuItem.customizations.sizes[0]
        val milkOption = testMenuItem.customizations.milkOptions[0]

        val cartItem = CartItem(
            menuItem = testMenuItem,
            selectedSize = sizeOption,
            selectedMilk = milkOption,
            selectedSugar = "Regular",
            quantity = 1
        )

        val expectedPrice = testMenuItem.basePrice + sizeOption.extraPrice + milkOption.extraPrice
        assertEquals(expectedPrice, cartItem.totalPrice, 0.01)
    }

    @Test
    fun testCartItemTotalPrice_withQuantity() {
        val sizeOption = testMenuItem.customizations.sizes[0]
        val milkOption = testMenuItem.customizations.milkOptions[0]

        val cartItem = CartItem(
            menuItem = testMenuItem,
            selectedSize = sizeOption,
            selectedMilk = milkOption,
            selectedSugar = "Regular",
            quantity = 3
        )

        val expectedPrice = (testMenuItem.basePrice + sizeOption.extraPrice + milkOption.extraPrice) * 3
        assertEquals(expectedPrice, cartItem.totalPrice, 0.01)
    }

    @Test
    fun testCartItemTotalPrice_withCustomizations() {
        val sizeOption = testMenuItem.customizations.sizes[1] // Medium with extra price
        val milkOption = testMenuItem.customizations.milkOptions[1] // Almond with extra price

        val cartItem = CartItem(
            menuItem = testMenuItem,
            selectedSize = sizeOption,
            selectedMilk = milkOption,
            selectedSugar = "Regular",
            quantity = 1
        )

        val expectedPrice = testMenuItem.basePrice + sizeOption.extraPrice + milkOption.extraPrice
        assertEquals(expectedPrice, cartItem.totalPrice, 0.01)
    }

    @Test
    fun testMenuItemDataStructure() {
        assertEquals(1, testMenuItem.id)
        assertEquals("Test Coffee", testMenuItem.name)
        assertEquals(4.85, testMenuItem.basePrice, 0.01)
        assertEquals(2, testMenuItem.customizations.sizes.size)
        assertEquals(2, testMenuItem.customizations.milkOptions.size)
        assertEquals(2, testMenuItem.customizations.sugarLevels.size)
    }

    @Test
    fun testSizeOption_extraPrice() {
        val smallSize = testMenuItem.customizations.sizes[0]
        val mediumSize = testMenuItem.customizations.sizes[1]

        assertEquals(0.0, smallSize.extraPrice, 0.01)
        assertEquals(0.65, mediumSize.extraPrice, 0.01)
    }

    @Test
    fun testMilkOption_extraPrice() {
        val oatMilk = testMenuItem.customizations.milkOptions[0]
        val almondMilk = testMenuItem.customizations.milkOptions[1]

        assertEquals(0.0, oatMilk.extraPrice, 0.01)
        assertEquals(0.50, almondMilk.extraPrice, 0.01)
    }
}
