# BitcoinTracker
Demo app that display the Bitcoin price for 2 weeks in 3 different currencies(USD, EUR, GBP).

# How to run the app
It's actually pretty simple, you can either clone the repo and run the app, or use this [apk](https://drive.google.com/file/d/1MlLtononk2Pz1jshXwtHiA8y3a7zYN_2/view?usp=sharing) directly.

## Stack
- **MVVM**\
  The purpos of using MVVM is that I want to separate the views from business logic and make use of the obsever pattern that livedata provides.
- **ViewBinding**
- **Dagger Hilt**\
  I know this project doesn't need dependency injection framework, but I used it to make my life easier.
- **Retrofit**
- **Gson**\
  I used Gson to parse the API response.
- **Coroutines**\
  I used Coroutines becuase it's easier when it comes to make asyncronous calls.
- **Room**\
  The reason for using Room is to cache the response from the API because I don't want to make multiple API calls everytime the user navigate between screens.
