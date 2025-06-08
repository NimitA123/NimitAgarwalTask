# 📊 NimitAgrawalTask - Holdings App

A simple and modern Android application built using **Jetpack Compose** that displays stock holdings with real-time P&L (Profit & Loss), using **MVVM Clean Architecture**, local caching, and test coverage.

---

## 🚀 Features

- ✅ Displays a list of user holdings with symbol, quantity, LTP, average price, etc.
- ✅ Expandable P&L card showing current value, investment, and returns.
- ✅ Pull-to-refresh for latest holding data.
- ✅ Offline support using Room database.
- ✅ Jetpack Compose UI with responsive layout.
- ✅ Loading shimmer effect while fetching data.
- ✅ Unit tests for ViewModel and Repository with fake dependencies.
- ✅ Error handling for no internet and edge cases.
- ✅ Modern architecture with clean separation of concerns.

---

## 🛠️ Tech Stack

- **Kotlin** (Latest version)
- **Jetpack Compose**
- **MVVM Clean Architecture**
- **Room (local storage)**
- **Retrofit** (API requests)
- **Coroutines** (Async/Flow)
- **Hilt** (Dependency Injection)
- **Material 3 Components**
- **JUnit** (Unit Testing)
- **Mockk / Mockito** for mocks

---

## 📷 Screenshots

| Holdings Screen | Profit & Loss Expand |
|-----------------|----------------------|
| ![Holdings](assets/holdings_screen.png) | ![P&L](assets/pnl_expand.png) |

---

## ✅ Acceptance Criteria Checklist

- [x] Min SDK >= 21
- [x] Compiles cleanly with latest Android Studio
- [x] No major UI or logical bugs
- [x] Responsive layout on multiple screen sizes
- [x] Test cases cover ViewModel, Repository, error states
- [x] Follows MVVM + Clean Architecture
- [x] Good naming conventions and code structure

---

## 🧪 Run Unit Tests

```bash
./gradlew testDebugUnitTest
