# 📊 NimitAgrawalTask - Holdings App

A simple and modern Android application built using **Jetpack Compose** that displays stock holdings with real-time P&L (Profit & Loss), using **MVVM Clean Architecture**, local caching, and test coverage.

---

## 🚀 Features

- ✅ Displays a list of user holdings with symbol, quantity, LTP, average price, etc.
- ✅ Expandable P&L card showing current value, investment, and returns.
- ✅ Offline support using Room database.
- ✅ Jetpack Compose UI with responsive layout.
- ✅ Loading shimmer effect while fetching data.
- ✅ Unit tests for ViewModel and Repository with fake dependencies.
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
![image](https://github.com/user-attachments/assets/1c898e41-00d7-4861-a917-c3869670a757)
![image](https://github.com/user-attachments/assets/33077db7-7362-41c9-ae45-223eaff71994)

## 🧪 Run Unit Tests

```bash
./gradlew testDebugUnitTest
