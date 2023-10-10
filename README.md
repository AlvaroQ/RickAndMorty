<h1 align="center">Rick and Morty Android App :iphone:</h1>
<p align="center">
    <a href="https://developer.android.com/reference"><img alt="Platform" src="https://img.shields.io/badge/platform-android-brightgreen.svg"/></a>
    <a href="https://developer.android.com/jetpack/androidx/releases/compose"><img alt="JetpackCompose" src="https://img.shields.io/badge/Jetpack%20Compose-1.5.3-blueviolet"/></a>
    <a href="https://dagger.dev/hilt/"><img alt="Hilt" src="https://img.shields.io/badge/hilt-2.47-yellow"/></a>
    <a href="https://detekt.dev/"><img alt="Detekt" src="https://img.shields.io/badge/detekt-1.3.0-blue"/></a>
    <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
</p>

<p align="center">
The purpose of this project is to reflect knowledge in Kotlin of MVVM pattern, coroutines, Flows, dagger hilt injector, Room with SQLite and COMPOSE! using https://rickandmortyapi.com/ public api.
</p> 

![banner](https://github.com/AlvaroQ/RickAndMorty/blob/main/capture/Landing.jpg)

## Kotlin - Clean Architecture following SOLID and MVVM :computer:

<p>Single Responsibility Principle, Open/Closed Principle, Liskov Substitution Principle, Interface</p> 
<p>Segregation Principle and Dependency Inversion Principle</p>
<p>Use cases with a CharacterRepository with Character and Local data sources</p> 
<p>Dependency management with Kotlin kts and Kotlin Symbol Processing (KSP) </p>
<p>Dagger Hilt dependency and Error handling with Arrow library.</p>

<p align="center">
  <img src="https://github.com/AlvaroQ/RickAndMorty/blob/main/capture/clean_architecture.jpg" width="250" alt="clean_architecture">
</p>

## Modules :bookmark_tabs:

* **::app** - The application module with access to **all the application**
* **::data** - Android module that **can only access domain module**
* **::domain** - Kotlin module that **cannot access any other module**
* **::usecases** - Android module that **can only access data and domain modules**

## Compose :cyclone:

Jetpack Compose and Compose Navigation

<table width="100%">
<tr>
<td width="50%" align="center">
CollapsingLayout, HorizontalPager and two tabs
</td>
<td width="50%" align="center">
Enter and exit transition with slide horizontally
</td>
</tr>
<tr>
<td width="50%" align="center">
<img src="https://github.com/AlvaroQ/RickAndMorty/blob/main/capture/collapsing_and_horizontal_pager.gif" width="50%" alt="collapsing_and_horizontal_pager"/>
</td>
<td width="50%" align="center">
<img src="https://github.com/AlvaroQ/RickAndMorty/blob/main/capture/transition.gif" alt="transition" width="50%"/>
</td>
</tr>

<tr>
<td width="50%" align="center">
Scale animation on enable favorite button
</td>
<td width="50%" align="center">
Showing cards sequentially on LazyColumn
</td>
</tr>
<tr>
<td width="50%" align="center">
<img src="https://github.com/AlvaroQ/RickAndMorty/blob/main/capture/expand_fav.gif" width="50%" alt="expand_fav"/>
</td>
<td width="50%" align="center">
<img src="https://github.com/AlvaroQ/RickAndMorty/blob/main/capture/sequentially_cards.gif" width="50%" alt="sequentially_cards"/>
</td>
</tr>
</table>

## Multi-filter with pagination :zap:

Filtered results with Status (All/Alive/Dead/Unknown), Gender(All/Female/Male/Genderless/Unknown)
and
naming with scroll-end pagination.
<p align="center">
  <img src="https://github.com/AlvaroQ/RickAndMorty/blob/main/capture/filters_and_paging.jpg" width="250" alt="clean_architecture">
</p>

## Local storage :floppy_disk:

Room store favorite on data base SQLite. I use the Entity 'Character' to save the characters
selected as favorites by the user and I manage queries in DAO FavoriteCharacterDao.

## Theme :sunny: / :first_quarter_moon_with_face:

Dark and light depending system theme
<pre><code>
@Composable
fun RickAndMortyTheme(darkTheme: Boolean = isSystemInDarkTheme(),content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }
}
</code></pre>

## Analysis tool :pill:

Detekt code analysis tool
<p align="center">
  <img src="https://github.com/AlvaroQ/RickAndMorty/blob/main/capture/detekt_report.jpg" width="75%" alt="Detekt">
</p>

## Libraries :book:

* [Compose][0] Toolkit for building native UI (in a declarative way - 100% Kotlin).
* [Compose Navigation][1] for tabs navigation using Jetpack Compose.
* [Material3][2] The next evolution of Material Design. Material 3 includes updated theming and
  components.
* [Coroutines][3] Library support for Kotlin coroutines.
* [Flows][4] Stream processing API, built on top of Coroutines.
* [Dagger Hilt][5] Dependency injection library for Android.
* [Retrofit][6] Type-safe REST client for Android to consume RESTful web services.
* [Coil Compose][7] Image downloading and caching library supported by Jetpack Compose.
* [Lottie Compose][8] Library that provides that parses Adobe After Effects animations exported as
  json with Bodymovin and renders them natively on mobile.
* [Arrow][9] Functional programming to Kotlin.
* [OkHttp3][10] Third party library for sending and receive network requests.
* [Room][11] The Room persistence library provides an abstraction layer over SQLite to allow for
  more robust database access while harnessing the full power of SQLite.
* [Detekt][12] Static code analysis tool for the Kotlin programming language.

[0]:  https://developer.android.com/jetpack

[1]:  https://developer.android.com/jetpack/compose/navigation

[2]:  https://developer.android.com/jetpack/androidx/releases/compose-material3

[3]:  https://github.com/Kotlin/kotlinx.coroutines

[4]:  https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/

[5]:  https://dagger.dev/hilt/

[6]:  https://github.com/square/retrofit

[7]: https://github.com/coil-kt/coil

[8]: https://airbnb.io/lottie/#/android-compose

[9]: https://apidocs.arrow-kt.io/

[10]: https://square.github.io/okhttp/

[11]: https://developer.android.com/jetpack/androidx/releases/room

[12]: https://detekt.dev/

## License :page_facing_up:

    Copyright 2023 Alvaro Quintana
    Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
    The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software. 
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
    http://www.apache.org/licenses/

<a href="https://www.buymeacoffee.com/alvaroqp" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png" alt="Buy Me A Coffee" style="height: auto !important;width: auto !important;" ></a>