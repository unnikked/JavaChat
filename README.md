JavaChat
========

A simple implementation of a chat using Java RMI

#USAGE
##Ver. 0.2
- Implementation of multichannel chat
- Configurements like __Ver. 0.1__

Command List:
- */list* : show the list of active channels
- */join* : join a channel and starts to chat

**NOTE**: for command */join* first digit it and then digit the name og channel

##Ver. 0.1
###Istructions for Linux users
Open a CLI and digit:
`rmiregistry`

Then open one terminal for server, as many terminals you want for clients:

`java ChatServer` on terminal for the server

`java ChatClient` on others terminals

You will have to choose a nickname. Simply type on the terminal and press ENTER to send your message, to stop programs press

`CTRL-C`

