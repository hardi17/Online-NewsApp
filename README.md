# Online News App
An online news app makes it easy to read news articles on the intdernet. It offers daily top headlines, lets you search for news based on your preferences, and categorizes news by sources, countries, and languages. 

![news_app_poster](https://github.com/hardi17/Online-NewsApp/assets/68413354/0a9db264-2cf2-4347-b0b2-978258770a99)

## Main Features 
- Kotlin : Used for Android development due to its modern features, concise syntax, null safety.
- Jetpack Compose : A modern UI toolkit for creating UI components declaratively.
- MVVM Architecture : Design pattern for separating UI, business logic, and data handling concerns.
- Unit and UI tests : To verify the app behave and ensure that they meet the expected functionality.
- Dagger- Hilt : Dependency management and promoting modularization.
- Coroutines : Simplify asynchronous programming for concurrent tasks.
- Flow API : Handles streams of data asynchronously and applies transformations.
- StateFlow : Represents a single updatable data value, commonly used for propagating state changes.
- Retrofit and OkHttp :   Networking libraries for making network requests to RESTful APIs.
- WebView : Component for displaying web content within applications.
- Instant Search : Real-time search feature providing immediate feedback to users.
- APK Optimization : Minimizes the size of Android application packages for improved performance.

## Dependencies 
- Coil : For image loading library.
- Jetpack compose : For native UI.
- Dagger-Hilt : For dependency injection.
- Mockito, JUnit and Turbine : For testing.

## Screens 
- Topheadline
- News listed based on selection:
    - Sources
    - Countries
    - Languages
- Search News

![Copy of Copy of linkedin post (2)](https://github.com/hardi17/Online-NewsApp/assets/68413354/e3bab195-1a3c-408b-9775-d6d1e70d3c29)

## Instructions to Run the Project
- Clone the Repository:

  ````
  git clone https://github.com/hardi17/Online-NewsApp.git
  cd Online-NewsApp
  ````

- Obtain your own API key from: https://newsapi.org

- Replace the API key

	- Open the ```app/build.gradle``` file and replace "<YOUR_API_KEY>" with your API key:
	   
	     ```
	      buildConfigField("String", "API_KEY", "\"<YOUR_API_KEY>\"")
	     ````

## Project Structure
```
|───com
    └───hardi
        ├───NewsApplication.kt
        │
        └───newsapp
            ├───data
            │   ├───api
            │   │       ApiKeyInterceptor.kt
            │   │       NetworkService.kt
            │   │
            │   ├───model
            │   │       Article.kt
            │   │       LocaleInfo.kt
            │   │       NewsSource.kt
            │   │       NewsSourcesResponse.kt
            │   │       Source.kt
            │   │       TopHeadlinesResponse.kt
            │   │
            │   └───repository
            │           CountriesRepository.kt
            │           LanguagesRepository.kt
            │           NewsSourcesRepository.kt
            │           SearchNewsRepository.kt
            │           TopHeadlineRepository.kt
            │
            ├───di
            │   ├───qualifiers.kt
            │   │
            │   └───module
            │           ApplicationModule.kt
            │
            ├───ui
            │   ├───base
            │   │       NewsNavigation.kt
            │   │       Route.kt
            │   │       UiState.kt
            │   │
            │   ├───common
            │   │       ArticleLayout.kt
            │   │       MessageLayout.kt
            │   │       SelectionListLayout.kt
            │   │       TopAppBarLayout.kt
            │   │
            │   ├───country
            │   │       CountriesViewModel.kt
            │   │       CountryListScreen.kt
            │   │
            │   ├───language
            │   │       LanguageListScreen.kt
            │   │       LanguagesViewModel.kt
            │   │
            │   ├───mainactivity
            │   │       MainActivity.kt
            │   │
            │   ├───newslist
            │   │       NewsListScreen.kt
            │   │       NewsListViewModel.kt
            │   │
            │   ├───newssources
            │   │       SourcesScreen.kt
            │   │       SourcesViewModel.kt
            │   │
            │   ├───search
            │   │       SearchNewsScreen.kt
            │   │       SearchViewModel.kt
            │   │
            │   ├───theme
            │   │       Color.kt
            │   │       Theme.kt
            │   │       Type.kt
            │   │
            │   └───topheadline
            │           TopHeadlineScreen.kt
            │           TopHeadlineViewModel.kt
            │
            └───utils
                    AppConstant.kt
                    DispatcherProvider.kt
                    ValidationUtils.kt
```
## Future Implementations
- Room database
- Pagination 
- WorkManager

# If this project helps you in anyway, show your love ❤️ by putting a ⭐ on this project ✌️

## Contributing to the News App project
Feel free to improve the current project and all pull requests are welcome 😄.

You can connect with me on:

LinkedIn : https://www.linkedin.com/in/hardi-r/

Medium : https://hardirachh.medium.com/
