

# Requirements
## Generally
You are supposed to write a multi-user chat using sockets, which allows any (but less than 50) number of clients to exchange messages.
Your server should be able to be started in two ways: <u>java ChatServer</u> and <u>java ChatServer <port></u>, whereby port 3000 is used by default in the first case
The clients should also be able to be started in two ways: <u>java ChatClient</u> and <u>java ChatClient [portNumber] [serverAddress]</u>, whereby in the first case localhost: 3000 is used by default.
The first message after a client has connected to the server consists only of a **string for the username** of this participant.

### There are 5 types of messages in total

<u>@username <blank> message</u> sends a DM to the relevant person and only to this person. If there is no one on the server with this name, only the sender receives an error message from the server.

If a user sends <u>WHOIS</u>, he / she (and only he / she) receives a list of currently connected users and since when they have been connected.

If a user sends <u>LOGOUT</u>, the connection of this user is closed and all streams and sockets on both sides are closed.

If a user sends <u>PENGU</u>, a cool penguin fact will be sent to all currently connected participants (creativity is welcome here :)).

<u>All other messages</u> are considered normal messages and are simply sent to all currently connected clients. Of course, you are welcome to consider other things. If you do this, you can, for example, send a welcome message to each client after connecting, in which all functions are listed.

# Server
You should implement a ChatServer that accepts connections using sockets.
For each incoming connection, you create a **<u>thread</u>** that is responsible for communication with this client.
For each incoming connection, a message is sent to all clients that a new user has joined the chat.
Your server always has an up-to-date **<u>data structure</u>** for the currently connected clients (don't forget the synchronization!).
If a client's socket is closed or an IO error occurs when a message is sent, this client is immediately removed from the list.

# Client
You should implement a ChatClient that establishes connections to the ChatServer using a socket.
The client should **<u>output</u>** all received messages on the console and **at the same time** allow the user to **<u>write</u>** their own messages at any time.

The client can choose his username for a session himself

# Editing tips and possibly helpful classes

Start with a chat that sends every message to all currently connected clients. For this, you can orient yourself on the presence task last week.
Now write a method for the server to send messages only to a specific user; if you solve this elegantly, you can use this method several times.

The classes **<u>ObjectInputStream</u>** and **<u>ObjectOutputStream</u>** allow you to send / receive objects from classes that implement the **<u>serializable interface</u>**.

To get the current time, you can use LocalTime.now().
Process that is easier to read