Login information
	Username      Password
	  test.........test
	  user........Password
	  worker......Password1

mysql-connector-java-5.0.47.jar
	can be found in the lib folder	

A.   Create a log-in form that can determine the user’s location and translate log-in and error control messages (e.g., “The username and password did not match.”) into two languages.
	Found in LoginController.Java. Second Language is French.


D.   Provide the ability to view the calendar by month and by week.
	Found in AppointmentMonthController and AppointmentWeekController.
 

E.    Provide the ability to automatically adjust appointment times based on user time zones and daylight saving time.
	Times are converted int AppointmentDAO.java
 

F.   Write exception controls to prevent each of the following. You may use the same mechanism of exception control more than once, but you must incorporate at least  two different mechanisms of exception control.

•   scheduling an appointment outside business hours
	Appointment times are selected using a combobox making it impossible to select times ouside of business hours

•   scheduling overlapping appointments
	This was handled using a both throw and a try catch.
	The try catch is in AddAppointmentController.java line 158 and throw is line 260
•   entering nonexistent or invalid customer data
	This was accomplished by using both throw ans try catch.
	the try catch is in AddAppointmentController.java line 121 and throw is line 211.


G.  Write two or more lambda expressions to make your program more efficient, justifying the use of each lambda expression with an in-line comment.
 	UpdateCustomerController.java line 174
	LoginController.java line 120

H.   Write code to provide an alert if there is an appointment within 15 minutes of the user’s log-in.
	found in LoginController.java line 125

I.   Provide the ability to generate each  of the following reports:

•   number of appointment types by month
	Found in ReportsController.java

•   the schedule for each consultant
	Found in ReportsController.java

•   one additional report of your choice
	Found in ReportsController.java


J.   Provide the ability to track user activity by recording timestamps for user log-ins in a .txt file. Each new record should be appended to the log file, if the file already exists.
	Found in LoginController.java line 107 and line 134