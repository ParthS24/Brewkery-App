# Brewkery - Android Coffee & Bakery Ordering App

A native Android coffee and bakery ordering app built with Kotlin and Jetpack Compose, powered by a REST API.

## Features

- **Menu Catalog**: Browse categorized items (Hot Coffee, Cold Brews, Artisan Bakery)
- **Item Details**: View detailed product information with customizations (size, milk options, sugar levels)
- **Shopping Cart**: Add items with customizations, manage quantities, view price breakdown
- **Order Status**: Track order with ticket ID and preparation status
- **Modern UI**: Clean Material Design 3 interface with smooth navigation

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Networking**: Retrofit 2
- **Async**: Kotlin Coroutines
- **Image Loading**: Coil
- **Navigation**: Jetpack Navigation Compose
- **State Management**: ViewModel with StateFlow
- **Architecture**: MVVM pattern

## API Endpoints

- Full Menu: `https://raw.githubusercontent.com/VivekShah138/Brewkery/main/data.json`
- Item Details: `https://raw.githubusercontent.com/VivekShah138/Brewkery/main/api/items/{id}.json`

## Installation

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle dependencies
4. Run on emulator or physical device (API 24+)

## Building APK

```bash
./gradlew assembleDebug
```

The APK will be located at: `app/build/outputs/apk/debug/app-debug.apk`

## Running Tests

```bash
./gradlew test
```

## Project Structure

```
app/src/main/java/com/example/brewkeryapp/
├── data/
│   ├── api/          # Retrofit API service
│   └── models/       # Data models (MenuResponse, MenuItem, CartItem, etc.)
├── ui/
│   ├── navigation/   # Navigation routes
│   ├── screens/      # Composable screens (Menu, ItemDetail, Cart, OrderStatus)
│   └── theme/        # App theme
├── viewmodel/        # BrewkeryViewModel
└── MainActivity.kt  # Main entry point with navigation setup
```

---

## AI Usage Documentation

This project was built with extensive assistance from AI tools, following Clickretina's AI-first development approach.

### AI Tools Used

1. **ChatGPT** - For taking Overview of the app and planning for all the screens along with the execution process. 
2. **Devin (Claude-based coding agent)** - Primary AI assistant for code generation, debugging, and architecture decisions
3. **Web Search Integration** - For API documentation and best practices lookup

### Example Prompts Sent to AI

**Prompt 1: Initial Project Setup**
> "I need to build a native Android coffee ordering app in Kotlin using Jetpack Compose. The app should have 4 screens: Menu (with categories and items), Item Detail (with customizations), Cart (with checkout), and Order Status. Use Retrofit for API calls, Coil for images, and Navigation Compose. The API endpoints are provided. Please help me set up the project structure and implement the screens efficiently."

**Prompt 2: ViewModel State Management**
> "Create a ViewModel for the Brewkery app that manages the cart state. It should support adding items with customizations (size, milk, sugar), updating quantities, removing items, calculating totals with delivery fee and tax, and placing orders. Use StateFlow for reactive state management."

**Prompt 3: Navigation Setup**
> "Set up Navigation Compose for the Brewkery app with 4 screens: Menu, ItemDetail (with itemId parameter), Cart, and OrderStatus (with ticketId parameter). Use a sealed class for screen routes and implement proper navigation between screens, including popping back to menu after order placement."

### What AI Got Right

1. **Clean Architecture**: AI suggested a well-structured MVVM architecture with clear separation of concerns (data layer, UI layer, navigation)
2. **StateFlow Implementation**: The ViewModel's StateFlow implementation for reactive UI updates was clean and idiomatic
3. **Navigation Pattern**: The sealed class approach for navigation routes was elegant and type-safe

### What AI Got Wrong and How I Fixed It

1. **Missing Import in MainActivity**: Initially, the AI forgot to add the `collectAsState` import when implementing the navigation setup. I manually added the import statement to fix the compilation error.
2. **Retry Button Handler**: In the MenuScreen error state, the AI initially passed `viewModel` as the onClick handler instead of a proper callback function. I corrected this by adding an `onRetry` parameter and passing it through from MainActivity.

### Learning Experience

Working with AI tools significantly accelerated development, allowing me to focus on business logic and UI polish while AI handled boilerplate code and architecture patterns. The iterative process of reviewing AI-generated code and making corrections helped me understand the codebase deeply, which is essential for the interview phase.

---

## Assignment Submission

This app was built as part of the Android Developer take-home assignment for Clickretina.

**Time Invested**: ~2 hours
**Approach**: Focused on clean architecture, efficient implementation, and smooth user experience rather than over-engineering.

📱 App Screenshots

<img width="396" height="816" alt="Screenshot 2026-10-09 122549" src="https://github.com/user-attachments/assets/008ee7f6-7b99-4020-be43-9838e35d20b6" />
<img width="398" height="825" alt="Screenshot 2026-10-09 122755" src="https://github.com/user-attachments/assets/03212c14-4c88-42cc-beb4-a7e2989a0253" />
<img width="417" height="821" alt="Screenshot 2026-10-09 122823" src="https://github.com/user-attachments/assets/7dbba27d-b745-4bcf-8dfa-7e89362895a2" />
<img width="417" height="818" alt="Screenshot 2026-10-09 122842" src="https://github.com/user-attachments/assets/0219faa7-69ac-4f7b-b23a-21ad4c2d9eea" />
<img width="430" height="828" alt="Screenshot 2026-10-09 122858" src="https://github.com/user-attachments/assets/b02ad4d7-a69b-4b91-b4b1-0923b2f6b0a7" />
<img width="436" height="826" alt="Screenshot 2026-10-09 122913" src="https://github.com/user-attachments/assets/fd9f8961-15a9-4f70-a831-3874df122c56" />








