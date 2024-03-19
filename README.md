### Daily-Feed
## Description:  
A Daily Feed app is a mobile application that delivers news content to users in a convenient 
and personalized way. The Daily Feed app is a comprehensive and user-friendly mobile application 
designed to provide users with a curated and personalized news feed experience. Its primary focus 
is to deliver the latest and most relevant news articles from various reputable sources around the 
world. Daily Feed apps have become increasingly popular in recent years due to the growing use 
of smartphones and mobile devices. They offer many advantages over traditional news sources, 
such as newspapers and television, by providing users with access to up-to-date news content from 
a variety of sources at any time and place. Daily feed apps typically provide a range of news 
categories, such as sports, entertainment, business, and technology, etc. Users can customize their 
news feeds by selecting the categories they are interested in and receiving notifications for 
breaking news stories or articles that match their preferences. One of the main benefits of news 
apps is the ability to provide a personalized and engaging user experience. Users can easily browse 
through articles, save stories they want to read later, and share content with their social networks. 
The Daily feed app should allow users to customize their news feeds by selecting the categories 
they are interested in. The Daily feed app should prioritize accuracy, credibility, and transparency 
in the news coverage. This can be achieved by fact-checking articles, providing clear attribution 
and sources, and avoiding the spread of fake news and misinformation. The Daily feed app should 
provide a range of news categories, such as sports, entertainment, business, and technology, among 
others. This will give users a comprehensive and diverse view of the news. The Daily feed app 
should have a user-friendly interface that is easy to navigate and understand. The app should also 
be optimized for mobile devices, with fast loading times and a visually appealing design. The app 
highlights trending topics and breaking news stories across the globe. Users can stay up to date 
with the most talked-about events and current affairs easily. The app provides a seamless and 
distraction-free reading experience, ensuring that users can focus on the content without any 
intrusive ads or pop-ups. Users can search for specific topics or news articles within the app's 
extensive database, making it easy to find information on past events or specific subjects. The app 
boasts a clean and intuitive interface that makes navigating through the news feed and accessing 
different features a seamless experience. The Daily Feed app aims to be a reliable source of news 
for users, keeping them informed about the world around them while catering to their individual 
interests and preferences.  

## Requirements Specifications: 
➢ Activity will contain buttons representing different categories of feed. 

➢ Each feed is presented as a card to the user. 

➢ Feed contains image and the headline of the content. 
➢ When a user clicks on the feed, it should redirect to provide the full article. 

➢ Application should contain authentication mechanism for authenticating and authorizing the user. 

➢ Activity should contain a search button with a progress dialog so that when user searches any text, all the feed containing the feed should show on the screen. 

➢ The feed will be read from an API and displayed to the user. 

➢ Each feed must contain a few features like headline, image, URL, etc. 

➢ The full article will be displayed on a new screen. 

➢ It also contains a functionality to manage all the expenses. 

➢ The screen will contain a Pie chart and the list of expenses and income below that. 

➢ It also contains an activity to add, delete an expense or income. 

➢ All the data is stored in the Firebase. 

➢ Authentication is done through the Firebase. 

➢ Contains a menu bar to switch between all the available functionalities like news, weather, and expense manager. 

➢ Weather activity must show the one day forecast along with the current day’s temperature. 

➢ It should be able to show the forecast of a city when searched for it. 

## Technical Description: 
In total, the application contains 7 activities which contain their own respective java files.

## Register Activity:  
The Firebase Auth instance (mAuth) is initialized using FirebaseAuth.getInstance(). The layout for the activity is inflated using ActivityRegisterBinding, which is a generated class that represents the 
binding between the activity and its XML layout. The TextView with the ID textView_login_click is given an OnClickListener to handle the click event. When clicked, it launches the Login activity and finishes the current Register activity. Inside the button_register's OnClickListener, the registration process 
is initiated when the user clicks the "Register" button. The input values (username, email address, password, and re-entered password) are retrieved from the respective TextInputEditText fields. Validation checks are performed to ensure that all the required fields are filled, and the entered  passwords match. If the validation passes, the app attempts to create a new  user account using mAuth createUserWithEmailAndPassword (email Address, password). This method asynchronously creates a new user account with the provided email address and password on Firebase. A listener is set to receive the result of the account creation attempt. If the account creation is successful (task.isSuccessful()), a toast message is displayed, indicating that the account was created successfully. If the account creation fails, an error toast message is displayed, indicating that the authentication failed. This code implements a user registration process using Firebase Authentication, ensuring that all required fields are filled correctly before attempting to create a new user account. The app communicates with the Firebase backend to handle user authentication and account creation processes.
The Activity looks like this:
![Register Activity](https://github.com/sriahri/Daily-Feed/blob/main/app/Screenshots/Register_.png)

## Login Activity:
The layout for the activity is inflated using ActivityLoginBinding, which is a generated class that represents the binding between the activity and its XML layout. The TextView with the ID textView_register_click is given an OnClickListener to handle the click event. When clicked, it launches 
the Register activity and finishes the current Login activity. Inside the button_login's OnClickListener, the login process is initiated when the user clicks the "Login" button. The input values (email address and password) are retrieved from the respective TextInputEditText fields. 
Validation checks are performed to ensure that both email address and password are provided. If the validation passes, the app attempts to sign in the user using mAuth.signInWithEmailAndPassword(emailAddress, password). This method asynchronously signs in the user with the 
provided email address and password on Firebase. A listener is set to receive the result of the sign-in attempt. If the sign-in is successful (task.isSuccessful()), the app navigates to the MainActivity using an explicit Intent. The current Login activity is finished to prevent the user from returning to the login screen with the back button. If the sign-in fails, an error toast message is displayed, indicating that the authentication failed. This code implements a user login process using Firebase Authentication. The app allows users to enter their email address and password, and upon successful  authentication, the user is redirected to the MainActivity. If the authentication fails, an error message is displayed. The app communicates with the Firebase backend to handle user authentication processes.
![Login Activity](https://github.com/sriahri/Daily-Feed/blob/main/app/Screenshots/Login_Screenshot.png)

## Main Activity:
The Firebase Auth instance (mAuth) is initialized using  FirebaseAuth.getInstance(). The onCreate method is overridden to set up the activity when it is created. The layout for the activity is inflated using 
ActivityMainBinding, which is a generated class that represents the binding between the activity and its XML layout. The onCreateOptionsMenu method is overridden to inflate the menu layout (menu_main.xml) in the app bar. The menu contains options like "Logout," "Expenses," and "Weather." The 
onOptionsItemSelected method is overridden to handle menu item clicks. For example, when the "Logout" option is selected, the user is logged out, and the app navigates to the login screen (Login activity). Similarly, selecting "Expenses" or "Weather" options opens their respective activities. The 
showNews method displays the retrieved news headlines in a RecyclerView with a custom adapter(CustomFeedAdapter). The adapter populates the news headlines in the UI, and each item is clickable, allowing users to view the full news details in the NewsDetailsActivity. The onClick method handles the category buttons' clicks ("Business," "Entertainment," "General," etc.). When clicked, the app retrieves news articles related to the selected category and displays them in the RecyclerView. The onNewsClicked method is called when a user clicks on a news headline. It navigates to the NewsDetailsActivity, passing the clicked headline's data. The searchView enables users to search for specific news topics. When a search query is submitted, the app retrieves news articles related to the search term and displays them in the RecyclerView. This code implements the main functionality of the Daily Feed app, allowing users to view and filter news articles based on different categories. It also provides search functionality and other app features accessible through the app bar menu options. The app communicates with a backend service to fetch news data and uses Firebase Authentication to handle user authentication. 
![Main Activity](https://github.com/sriahri/Daily-Feed/blob/main/app/Screenshots/News.png)

## NewsDetails Activity:
The layout for the activity is inflated using ActivityNewsDetailsBinding, which is a generated class that represents the binding between the activity and its XML layout. The activity receives the selected news article's data as an extra from the previous activity (most likely the MainActivity) using getIntent().getSerializableExtra("data"). The data is deserialized into a Headlines object, which represents a news article with various attributes like title, author, published time, description, content, and the URL of the news image. The UI elements (TextView and ImageView) are populated with the relevant data from the Headlines object: textView_details_title displays the news article's title.  imageView_details_news displays the news article's image using Picasso, a powerful image loading library for Android. textView_details_author displays the news article's author. textView_details_time displays the news article's published time. textView_details_details displays the news article's description. 
textView_details_content displays the news article's content. The Picasso.get().load(headlines.getUrlToImage()).into(imageView_details_news) line loads the news article's image from the provided URL (getUrlToImage()) using Picasso and sets it to the imageView_details_news. This code implements the detailed view of a selected news article in the Daily Feed app. When a user clicks on a news headline in the MainActivity, this activity is launched, and it displays the full details of the selected news article, including its title, author, published time, description, content, and image. The app uses Picasso to efficiently load and display the news article's image from its URL.

![NewsDetails Activity](https://github.com/sriahri/Daily-Feed/blob/main/app/Screenshots/News_details_activity.png)

## Weather Activity:
The Firebase Auth instance (mAuth) is initialized using FirebaseAuth.getInstance(). weatherModelArrayList and weatherAdapter are used to store and display weather details in the RecyclerView. The layout for the activity is inflated using ActivityWeatherBinding, which is a generated class that represents the binding between the activity and its XML layout. The app checks for 
location permission, and if not granted, requests the necessary permissions. The app retrieves the user's current location using the LocationManager and calls getCityName() to get the city name based 
on the latitude and longitude. The getWeatherDetails(String city) method is called to fetch weather details from a weather API (weatherapi.com) using Volley, which is an HTTP library for Android. 
The weather details, including temperature, condition (e.g., sunny, cloudy), and weather icon, are displayed in the UI. The background image is set based on whether it's day or night. Users can enter a city name in the TextInputEditText field and click the search button (search) to fetch weather details for the specified city. The onRequestPermissionsResult method handles the result of the permission request and displays a toast message if the permission is not granted. The onCreateOptionsMenu method is overridden to inflate the menu layout (menu_main.xml) in the app bar. The menu contains options like "Logout," "Expenses," "Weather," and "News." The onOptionsItemSelected method handles menu item clicks. For example, selecting "Logout" logs out the user, navigating them to the login screen (Login activity). The "Expenses" and "Weather" options open their respective activities. Selecting "News" navigates to the MainActivity. This code implements the WeatherActivity of the Daily Feed app, providing users with weather information based on their location or a city name input. The app communicates with a weather API to fetch real-time weather data and uses Volley to handle HTTP requests. Additionally, the app uses 
Firebase Authentication for user login/logout functionality. 
![Weather Activity](https://github.com/sriahri/Daily-Feed/blob/main/app/Screenshots/weather.png)

## Expense Activity:
The layout for the activity is inflated using ActivityExpenseBinding, which 
is a generated class that represents the binding between the activity and its 
XML layout. The activity sets up the expenseAdapter for the RecyclerView 
and fetches expense data from the Firestore database using Firebase 
Firestore. The getData() method fetches the expenses from Firestore based 
on the user's uId (user ID). The expenses are categorized as "Income" or 
"Expense," and the total amounts for each category are calculated. The 
setGraph() method sets up the pie chart with the calculated income and 
expense amounts. The pie chart is displayed using the MPAndroidChart 
library. The onClick() method is called when an expense item is clicked, 
allowing the user to edit the selected expense. The onResume() method is 
overridden to refresh the expense data and pie chart when the activity resumes. The 
onCreateOptionsMenu method is overridden to inflate the menu layout (menu_main.xml) in the 
app bar. The menu contains options like "Logout," "Expenses," "Weather," and "News." The 
onOptionsItemSelected method handles menu item clicks. For example, selecting "Logout" logs 
out the user, navigating them to the login screen (Login activity). The "Expenses," "Weather," and 
"News" options open their respective activities. This code implements the ExpenseActivity of the 
Daily Feed app, displaying a list of expenses and incomes fetched from Firebase Firestore and 
showing them in a RecyclerView. Additionally, it provides a pie chart to visualize the total expense 
and income amounts. Users can add new expenses or incomes by clicking on respective buttons, 
which leads them to the AddExpenseActivity. The app also supports user authentication through 
Firebase Auth, allowing users to log in and out. 
![Expense Activity](https://github.com/sriahri/Daily-Feed/blob/main/app/Screenshots/Expense.png)

## Add Expense Activity:
The layout for the activity is inflated using ActivityAddExpenseBinding, 
which is a generated class that represents the binding between the activity 
and its XML layout. The activity checks whether it is for adding a new 
expense or editing an existing one based on the type variable and expense 
object. If it's for editing an existing expense, the existing expense data is 
pre-filled into the respective fields. The activity sets up OnClickListener 
for the "Income" and "Expense" radio buttons to allow the user to select 
the type of the expense. The "Add" button's click listener is set up to 
create a new expense if the expense object is null or update an existing 
expense if the expense object is not null. The "Delete" button's click 
listener is set up to delete the existing expense if the user chooses to delete 
it. The createExpense() method is called when the user clicks the "Add" 
button to create a new expense. It collects the expense details from the 
UI fields, creates a new Expense object, and saves it to the Firebase 
Firestore database. The updateExpense() method is called when the user 
clicks the "Add" button to update an existing expense. It collects the 
updated expense details from the UI fields, creates a new Expense object, and updates the 
corresponding expense entry in the Firebase Firestore database. The deleteExpense() method is 
called when the user clicks the "Delete" button to delete an existing expense from the Firebase 
Firestore database. This code implements the AddExpenseActivity of the Daily Feed app, allowing 
users to add new expenses or edit existing expenses. The expense details are collected from the UI 
fields and saved to or updated in the Firebase Firestore database accordingly. The user can also 
delete an existing expense if needed.

![Add Expense Activity](https://github.com/sriahri/Daily-Feed/blob/main/app/Screenshots/Add_expense.png)

## References:
[Volley](https://github.com/google/volley)

[Square](https://square.github.io/retrofit/)

[Picasso](https://github.com/square/picasso)

[Firebase](https://firebase.google.com/docs/auth/android/password-auth#java_3)

[News API](https://newsapi.org/)

[Retrofit](https://github.com/square/retrofit/blob/master/retrofit-converters/gson/README.md)

[Android developer studio](https://developer.android.com/studio/run/device)

[Weather API](https://weatherapi.com/)
[MP Android Chart](https://github.com/PhilJay/MPAndroidChart)
