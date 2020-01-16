## General info
The "Wheel of Fortune" game for two players modeled on the popular TV game show.
Players can see events (e.g. turning the wheel) at the same time on both screens.
Connecting between players is carried out using socket server.
	
## Technologies/Libraries
Project is created with:
* Java 8
* Java FX
* JDBC
* JUnit

## Setup
Instructions for starting the application on one computer - two Eclipses and two workspaces
* Import the project from the workspaceA directory into the first Eclipse workspace
* Import the project from the workspaceB directory into the second Eclipse workspace
* Start the server by running the SocketServer.java in the first Eclipse
* Start the application by running the Main.java class in the first Eclipse
* In the displayed interface, click the Start button, enter the player's nickname and click OK, then the main game interface will be displayed.
* Start the application by running the Main.java class in the second Eclipse
* Player A starts the game by pressing the "Spin the wheel" button and spinning the wheel with the mouse.

To run the game on two computers you have to change the host giving the IP number in "gameWindowController" class.

## Status
Finished
