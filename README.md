# Description of BudgetTracking APP

*Group members names:

BOUANANE Hajar  hjr2.bouanane@gmail.com

RACHIDY Marouane marouanerachidy@gmail.com

*Subject : Budget Tracking App

Description of the App:
The "BudgetTracker" application allows the user to instantly track their spending and cash inflows.
It tracks its location, the user has the possibility to add a picture of the receipt if he want, and records it for each new expense.
It also allows him to have an overview using a graph of the way he manages his money by displaying his expenses by year and by category for example (House, food, credit, rent ...)


*Description of the activity we used:

- MainActivity:
 	This activity allows you to create an account to access the application by entering a username and password.

- LoginActivity:

	In this activity we verify the identifiers (username and password) entered by the user and then if they are valid we give him access to his account.
In this same activity, we create a periodic notification (every week) which notifies the user of the state of his account (for the demo we reduced the time to 10 seconds).

-HomeActivity:

	This activity represents our user's home page. It display his incomes (Salary ..),his expenses as well as her account state.
From this activity as well as all the ones that follow we have integrated a menu (Drawer) which allows our user to navigate between the different activities easily.


-Addincome:

	This activity allows you to add an incom.

-AddExpens:

	This activity allows you to add an expense that takes place over several stages
Step 1 retrieve the fields entered by the user and redirect the user to the second activity which is the map (MapsActivity).


-MapsActivity:

	Step 2 : Allows the expense to be saved with the current location of the user by recording the latitude and longitude.

-ViewExpens:

	Displays the different expenses as a list.

-ExpensActivity:

	In this activity we display the details of the expense selected in the previous activity (ViewExpens)
and through a button the user has the ability to link the expense to a receipt photo (Via the camera).

-Chart:
	This activity gives the user the ability to access the various graphs made available to them which represent their expenses.
it calls two class PieChartActivity and BarChartActivity. To represent the graphs we used the library "'com.github.PhilJay: MPAndroidChart: v3.1.0'".

-DBHelper:
	It is a class that manages the creation, update and suppression of the database by executing SQL queries.


-Aboutus:
 	This activity is a short description of the project (text).

*Intent:
	In this project we used several intents which allows to interact between several activities. For example, we used one to
 transmit the information entered by the user in the Add expens activity to the MapActivity.

*SENSOR used:

	We used geolocation to find the current position of the user and camera that allows the user to take the picture of a receipt.

*Background Services/Threads:

	A BroadCast RECEIVER was used to schedule reporting notifications periodically (weekly). Also as a background service we used jobscheduler
Jobscheduler: to constantly check the status of the account and notify the user if he is are overdrawn; this check is done every 15 mins.
