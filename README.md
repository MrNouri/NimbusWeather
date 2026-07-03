<div align="center">

# 🌤️ Nimbus Weather

**A beautifully crafted, modern weather application for Android.**

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Ready-success.svg?logo=android)](https://developer.android.com/jetpack/compose)
[![Architecture](https://img.shields.io/badge/Architecture-Clean%20%2B%20MVI-orange.svg)]()
[![Code Score](https://img.shields.io/badge/Code%20Quality-9%2F10%20(A)-brightgreen.svg)]()
</div>

<br/>

## 📖 Overview
**Nimbus Weather** is an elegantly designed weather application built to demonstrate modern Android development practices. It features a stunning **Glassmorphic UI**, dynamic color adaptations, and butter-smooth animations, bringing both beauty and performance to the palm of your hands.

This project is not just about weather; it's a showcase of **production-ready architecture**, making it a perfect reference for developers and a testament to high-quality software engineering for employers.

---

## ✨ Features
* 📱 **Modern UI/UX:** Built entirely with Jetpack Compose featuring `chrisbanes/haze` for real-time glassmorphism and blur effects.
* 🎨 **Dynamic Theming:** Status bar and UI elements adapt dynamically based on the brightness of the fetched weather wallpaper.
* 🚀 **Interactive Animations:** Features a fluid, velocity-tracked vertical drag gesture for expanding the weather card.
* 🖌️ **Custom Canvas Graphics:** Every single loading animation and visual effect is meticulously built from scratch using native Canvas APIs.
* 🏗️ **Clean Architecture & MVI:** Strict separation of concerns across Data, Domain, and Presentation layers ensuring high scalability and testability.
* 🌐 **Robust Networking:** Built with Ktor client, handling API calls seamlessly with customized error mapping and exception handling.
* 💾 **Offline-first Capabilities:** Caches favorite cities using Room database and manages user settings via DataStore Preferences.
* 🎯 **Empty State Handling:** Intuitive onboarding screen for new users before adding their first city.

---

## 🛠️ Tech Stack & Libraries

This project uses the bleeding-edge Android tech stack for 2026:

| Category | Technology / Library |
| :--- | :--- |
| **Language** | [Kotlin](https://kotlinlang.org/) |
| **UI Toolkit** | [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3) |
| **Architecture** | Clean Architecture, MVI / MVVM (Unidirectional Data Flow) |
| **Dependency Injection** | [Koin](https://insert-koin.io/) |
| **Networking** | [Ktor Client](https://ktor.io/) + Kotlinx Serialization |
| **Local Storage** | [Room](https://developer.android.com/training/data-storage/room) & [DataStore Preferences](https://developer.android.com/topic/libraries/architecture/datastore) |
| **Asynchrony** | [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) & [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) |
| **Image Loading** | [Coil 3](https://coil-kt.github.io/coil/) |
| **Visual Effects** | [Haze](https://github.com/chrisbanes/haze) (for Glassmorphism) |

---

## 🏆 Code Quality: 9/10 (A Grade)
This repository has been evaluated by an Advanced AI Code Reviewer and achieved a stellar **9/10 (A)** score. 
The review highlighted:
* ✅ Exceptionally clean separation of concerns.
* ✅ Professional use of Coroutines and Flow for reactive programming.
* ✅ Mastery over Jetpack Compose's advanced animation and gesture APIs.
* ✅ Production-ready exception handling and network wrappers.

---

## ⚙️ Getting Started

### Prerequisites
To build and run this project, you need:
* Android Studio (Latest Version)
* JDK 17+

### API Keys Setup
This app uses **OpenWeatherMap** for weather data and **Pexels** for dynamic wallpapers. You will need to add your API keys to the `local.properties` file in the root directory.

Create or edit `local.properties` and add:
```properties
openWeatherApiKey="YOUR_OPEN_WEATHER_API_KEY"
pexelsApiKey="YOUR_PEXELS_API_KEY"
```

### Build & Run
1. Clone the repository:
   ```bash
   git clone https://github.com/YourUsername/NimbusWeather.git
   ```
2. Open the project in Android Studio.
3. Sync Gradle and run the app on an emulator or physical device.

---

## 🤝 Contributing
Contributions, issues, and feature requests are welcome!

## 📝 License
This project is licensed under the MIT License.
