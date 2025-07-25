# PosNativeApp

PosNativeApp is a modular Android Point of Sale (POS) application built using Kotlin, Jetpack Compose, and Clean Architecture. It separates concerns clearly across different modules for maintainability, scalability, and testability.

---

## ✨ Architecture Diagram

```mermaid
graph TD
    A[app] --> B[feat]
    B --> C[domain]
    C --> D[core]
    C --> E[offline]
    C --> F[client]
    B -->|implements| C
    D --> C
    E --> C
    F --> C
    B --> G[navigation]
    B --> H[components]
    D --> H
    D --> G
```

---

## 📂 Modules Overview

### 1. `app`
- Main entry point (contains `MainActivity.kt` and theming)
- Starts dependency injection and navigation

### 2. `domain`
- Pure Kotlin module
- Contains business logic contracts, data models, and exception classes
- Defines interfaces (e.g., `AuthRepository`)

### 3. `data`
- Implements `domain` interfaces
- Maps DTOs to domain models
- Provides API services and dependency modules

### 4. `client`
- Sets up Retrofit, OkHttp, Moshi
- WebSocket implementation and interceptors
- Network monitors and service providers

### 5. `offline`
- Manages local storage via DataStore
- Token and preference management

### 6. `core`
- Shared utilities and constants
- State machine logic for managing UI state

### 7. `feat/*`
- Feature-specific presentation modules
- Uses Jetpack Compose
- Examples: `feat/auth`

### 8. `navigation`
- Central route definitions
- Handles cross-feature navigation

### 9. `components`
- Shared UI components (buttons, inputs, etc.)

### 10. `buildSrc`
- Centralized build configuration
- Contains build variants, flavor setup, versions

### 11. `test`
- Contains unit and UI test setups for core/client/ui logic

---

## ▶️ Build & Run

```bash
git clone https://github.com/GradleBuildTech/PosNativeApp.git
cd PosNativeApp
./gradlew clean assembleDebug
```

---

## 🔧 Technologies

- Kotlin + Coroutines
- Jetpack Compose
- Hilt (DI)
- Retrofit + OkHttp + Moshi
- DataStore
- WebSocket (custom impl)
- MVVM + Clean Architecture

---

## 📘 Modules in PosNativeApp

| 📁 Module | 📄 README | 🔗 Link |
|----------------------|--------------------|--------|
| 🛰️ Client Core (Network) | Network Guide | [Network_readme.md](https://github.com/GradleBuildTech/PosNativeApp/blob/main/client/Network_readme.md) |
| 🧩 Client Core (Module Structure) | Module Overview | [Module_readme.md](https://github.com/GradleBuildTech/PosNativeApp/blob/main/client/Module_readme.md) |
| 🔐 Network Layer (Interceptors) | Request/Response Interceptors | [README_Interceptors.md](https://github.com/GradleBuildTech/PosNativeApp/blob/main/client/README_Interceptors.md) |
| 🌐 Realtime Communication | Custom Socket Handling | [README_Socket.md](https://github.com/GradleBuildTech/PosNativeApp/blob/main/client/src/main/java/com/example/client/socket/README_Socket.md) |
| 🧭 Navigation System | Navigation Setup & Usage | [NAVIGATION_MODULE_README.md](https://github.com/GradleBuildTech/PosNativeApp/blob/main/navigation/NAVIGATION_MODULE_README.md) |
| 📦 Offline Storage | Local cache & handling logic | [OFFLINE_MODULE_README.md](https://github.com/GradleBuildTech/PosNativeApp/blob/main/offline/OFFLINE_MODULE_README.md) |


## 🚮 License

MIT License

