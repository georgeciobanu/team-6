REQUIREMENTS:
--------------------------------------------------
* Netbeans 5.5.1 + JDK
* Java Runtimes 1.6
* MS Access 2003
* MS Windows

NOTE: If you get a frozen AdminCLient or EndUserClient, please go to:
Control Panel - > Java -> Advanced Tab -> Debugging and make sure that the following settings are as follows
-Enable Logging - CHECKED
-Enable Tracking - UNCHECKED
- Show applet lifecycle exceptions - CHECKED

Control Panel - > Java -> Advanced Tab -> Java Console and make sure that the following settings are as follows
Show Console - Selected
Hide Console - Selected 
Do not start console - NOT SELECTED <<<< Critical
The buttons behave weird, but it's easy to make this selection.

Before anything else: In the same folder as the files ACEEndUserClient, ACEServer and ACEAdminCLient are, please create a folder named Lib and
copy inside the files 

FundamentalsLib.jar
swing-layout-1.0.jar

These files are distributed with the application.


HOW TO RUN TEAM 6'S ACE: (without NetBeans)
--------------------------------------------------
0. Make sure you have Microsoft Access installed on the computer.

SETUP THE DATABASE SERVER
1. Go to the Control Panel
2. Go to Administrative Tools
3. Go to Data Sources (ODBC)
4. Click on the 'Add' button
5. Select the 'Microsoft Access Driver (*.mdb)'
6. Click on the 'Finish' button
7. Write 'acedb' (without quotes) in the Source Name and leave Description empty
8. Click on the 'Select...' button
9. Find the main.mdb file that is located in the Database folder of Group6
10. Click the Advanced button
11. Enter admin as the username and admin as the password
10. Click 'OK'
12. Click 'OK' again.

End of DB setup


RUNNING THE PROJECTS
1. Double click AceSERVER.jar
2. If Windows bugs you with any security questions, allow the program to do anything it requires.
3. Double-click AceEndUserClient
4. Double-click AceAdminClient

USING ACEEndUserCLient:
1. In ACEEnduserCLient click Login (username should be gabriel and password who)
2. It should connect to the server.
3. You can run as many clients at the same time as you want. This is also true for AdminClient.


REQUIREMENTS:
--------------------------------------------------
* Netbeans 5.5.1 + JDK
* Java Runtimes 1.6
* MS Access 2003
* MS Windows



HOW TO RUN TEAM 6'S ACE: (With NetBeans)
--------------------------------------------------

SETUP THE DATABASE SERVER AS ABOVE

RUNNING THE PROJECTS
1. Open the following three projects in Netbeans
	- ACEAdminClient
	- ACEEndUserClient
	- ACEServer

2. Start the ACEServer
3. Start one of ACEAdminClient or ACEEndUserClient

Login with the username/password presented. If you would like to try other usernames, please see the users table in the database.


SVN repository:
--------------------------------------------------
URL: https://team-6.googlecode.com/svn/trunk/
Username: geo.ciobanu
Password: g8f5k7n9



Wiki:
--------------------------------------------------
URL: http://www.ecse321.com/
Username: ecse
Password: tavares321