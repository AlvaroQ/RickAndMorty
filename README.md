# RickAndMorty

![banner](https://github.com/AlvaroQ/RickAndMorty/blob/main/capture/Landing.jpg)

An Compose Application example using https://rickandmortyapi.com/

# Kotlin - Clean Architecture following SOLID principles and MVVM presentation pattern

Single Responsibility Principle, Open/Closed Principle, Liskov Substitution Principle, Interface
Segregation Principle and Dependency Inversion Principle
Use cases with a CharacterRepository and Character and Local data source
Gradle’s Kotlin DSL and Kotlin Symbol Processing (KSP)

<img src="https://github.com/AlvaroQ/RickAndMorty/blob/main/capture/clean_architecture.jpg" width="250" alt="clean_architecture">

## Compose

Jetpack Compose and Compose Navigation
CollapsingLayout with HorizontalPager (two tabs)
<p align="center">
  <img src="https://github.com/AlvaroQ/RickAndMorty/blob/main/capture/collapsing_and_horizontal_pager.gif" width="250" alt="collapsing_and_horizontal_pager">
</p>

Enter and exit transition (slide horizontally)
<p align="center">
  <img src="https://github.com/AlvaroQ/RickAndMorty/blob/main/capture/transition.gif" width="250" alt="transition">
</p>

Scale animation on enable favorite button
<p align="center">
  <img src="https://github.com/AlvaroQ/RickAndMorty/blob/main/capture/expand_fav.gif" width="250" alt="expand_fav">
</p>

Showing cards sequentially on LazyColumn
<p align="center">
  <img src="https://github.com/AlvaroQ/RickAndMorty/blob/main/capture/sequentially_cards.gif" width="250" alt="sequentially_cards">
</p>

## Multi-filter

Filtered results with Status (All/Alive/Dead/Unknow), Gender(All/Female/Male/Genderless/Unknow) and
naming with scroll-end pagination.

<img src="https://github.com/AlvaroQ/RickAndMorty/blob/main/capture/filters_and_paging.jpg" width="250" alt="clean_architecture">

## Injector and Android Architecture Components (AAC)

Dagger Hilt dependency, States updates are immediately reflected in the UI by the use of observable
state holders, like StateFlow

## Error handling

I use the Arrow library to handle the error/result pair as follows:

<pre><code>Either<Error, Result></code></pre>
<pre><code>UseCase().fold({ exception -> { } }, { result -> { }})</code></pre>

## Local storage

Room store favorite on data base SQLite. I use the Entity 'Character' to save the characters
selected as favorites by the user and I manage queries in DAO FavoriteCharacterDao.

## Theme

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

## Analysis tool

Detekt code analysis tool
<img src="https://github.com/AlvaroQ/RickAndMorty/blob/main/capture/detekt_report.jpg" width="600" alt="Detekt">

## License

    Copyright 2023 Alvaro Quintana

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

<a href="https://www.buymeacoffee.com/alvaroqp" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png" alt="Buy Me A Coffee" style="height: auto !important;width: auto !important;" ></a>