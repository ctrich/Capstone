# Dentist Appointment Scheduler

## Login information
	Username      Password
	  test.........test
	  user........Password
	  worker......Password1
![Login](https://user-images.githubusercontent.com/41929486/181086470-059bd084-af2a-4554-849f-981eb950c726.png)

mysql-connector-java-5.0.47.jar
	can be found in the lib folder	

### Create a log-in form that can determine the user’s location and translate log-in and error control messages (e.g., “The username and password did not match.”) into two languages.
	Found in LoginController.Java. Second Language is French.


### Provide the ability to view the calendar by month and by week.
	Found in AppointmentMonthController and AppointmentWeekController.
 ![Reports App by month](https://user-images.githubusercontent.com/41929486/181087464-49aca55c-64c5-4fc5-86a0-352fa3ababdb.png)


### Provide the ability to automatically adjust appointment times based on user time zones and daylight saving time.
	Times are converted in AppointmentDAO.java
 

### Write exception controls to prevent each of the following. You may use the same mechanism of exception control more than once, but you must incorporate at least  two different mechanisms of exception control.

•   scheduling an appointment outside business hours
	Appointment times are selected using a combobox making it impossible to select times ouside of business hours

•   scheduling overlapping appointments
	This was handled using a both throw and a try catch.
	The try catch is in AddAppointmentController.java line 158 and throw is line 260
	
![validation 3 Overlap](https://user-images.githubusercontent.com/41929486/181089375-8146f0f0-8f1b-43a6-806a-12aa8ce8fe33.png)


•   entering nonexistent or invalid customer data
	This was accomplished by using both throw ans try catch.
	the try catch is in AddAppointmentController.java line 121 and throw is line 211.
![validation 1 empty](https://user-images.githubusercontent.com/41929486/181089189-96aed86e-51ac-4f44-9f01-6cadd09e9242.png)

	


### Write two or more lambda expressions to make your program more efficient, justifying the use of each lambda expression with an in-line comment.
 	UpdateCustomerController.java line 174
	LoginController.java line 120

### Write code to provide an alert if there is an appointment within 15 minutes of the user’s log-in.
	found in LoginController.java line 125

### Provide the ability to generate each  of the following reports:

•   number of appointment types by month
	Found in ReportsController.java

•   the schedule for each consultant
	Found in ReportsController.java

•   one additional report of your choice
	Found in ReportsController.java


### Provide the ability to track user activity by recording timestamps for user log-ins in a .txt file. Each new record should be appended to the log file, if the file already exists.
	Found in LoginController.java line 107 and line 134

#### More screenshots
![Search patient main](https://user-images.githubusercontent.com/41929486/181089952-3a9213f2-befa-4a27-b823-333bbe80ee15.png)


![Search patient appointment](https://user-images.githubusercontent.com/41929486/181089964-5c6e47d5-d12a-4212-bd6b-931bdf02b69a.png)

