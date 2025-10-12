# Distributed File Sharing System with UDP datagram transfer between nodes

<p align="center">
  <img src="assets/torrent-animation.gif" alt="Torrent Animation" />
</p>


## Overview

This project implements a distributed file sharing system using Java socket programming. It consists of a server and multiple clients that can share files with each other.

## Features

- File Fragmentation: Files are split into blocks to facilitate efficient sharing.
- UDP Communication: UDP is used for efficient block information exchange among clients.
- Mediator Thread: A mediator thread coordinates the exchange of block information between clients and prioritizes those with better connection.
- Menu System: Clients can interact with the system through a simple console menu.
- DNS system: If you configure bind9 in your system you can connect the nodes through their domain names.

## Limitations

- Since this is only a proof of concept, all the files are split into 962 bytes blocks, and the identifier for them goes up to 9999 so only around 9mb are allowed per file you want to transfer.
- If there is one node that has all the blocks and it has the best connection, it is prioritized above all the others and the transfer will never be distributed as to lower the load on the node.

## Components
### Server

The server listens for incoming connections from clients, manages client files and block information, and facilitates communication between clients. 

Usage:

```
java Server
```


### Client

Clients connect to the server, share their files, and interact with the distributed file system through a console menu. Also they have a mediator thread that manages the UDP communication between themselves, ensuring efficient exchange of block information.

Usage:

```
java Client
```

### DNS Files

If you're using the DNS version of this project, you should install bind9 beforehand and dump the files inside "/etc/bind/" so there's a working name resolution.

## How to Use

    Start the Server:
        Run the Server class to start the server.

    Connect Clients:
        Run the Client class on multiple machines to connect clients to the server.

    Share Files:
        Clients can share files by placing them in the ClientFiles directory.

    Interact with the Menu:
        Clients can interact with the system through the console menu:
            Option 1: Ask for a file location.
            Option 2: Download a file.
            Option 3: Exit.

Dependencies

    Java SDK 8 or later.

Notes

    The project is currently configured to use the default server IP address (10.0.0.10) or Domain Name (Servidor1.cc23) and port (9090). Update these values in the Client class if needed.

Contributors

    Luís Ferreira a.k.a. NopeGuy
