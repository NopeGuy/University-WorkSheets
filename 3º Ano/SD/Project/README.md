# SD 23-24

## Overview

This document provides additional information about the server components in the Java-based client-server application. The server components include:

1. **CloudServer**: The main server responsible for managing connections with remote servers, handling client requests, and coordinating task execution.

2. **StandaloneServer**: A standalone server that executes tasks received from the CloudServer using the provided JobFunction.

3. **Task**: Represents a task to be executed by the StandaloneServer. It contains information such as the task ID, data to be processed, and the connection through which results are sent back to the CloudServer.

4. **Users**: Manages user accounts and provides functionality for user authentication and registration.

5. **Servers**: Manages server configurations, allowing the addition and removal of servers from the system.

## CloudServer

The `CloudServer` class is the main server that listens for incoming connections on port 10080. It manages connections with both remote servers (`RemoteServer`) and clients (`Connection`). The key functionalities include:

- Handling client login and registration requests.
- Receiving and processing task requests from clients.
- Coordinating the execution of tasks by allocating them to available remote servers.
- Managing the status of connected servers.
- Handling disconnections and removing servers from the system.

## StandaloneServer

The `StandaloneServer` class represents a standalone server that connects to the CloudServer. It continuously listens for incoming frames, executes tasks using the provided `JobFunction`, and sends the results or errors back to the CloudServer.

## Task

The `Task` class encapsulates information about a task to be executed by the StandaloneServer. It includes the task ID, data to be processed, and the connection through which results are sent back to the CloudServer.

## Users

The `Users` class manages user accounts, allowing for user authentication and registration. It provides methods to validate user credentials, add new accounts, and serialize/deserialize user data to/from a file.

## Servers

The `Servers` class manages server configurations, allowing the addition and removal of servers from the system. It provides methods to add servers, remove servers, and serialize/deserialize server data to/from a file.

## How to Run

1. Compile the Java files: `javac Service/*.java`.
2. Run the CloudServer: `java Service.CloudServer`.
3. Run the StandaloneServer: `java Service.StandaloneServer [memory]`.
4. Run the Client: `java Service.Client`

Note: The StandaloneServer accepts an optional command-line argument for memory specification. If not provided, a default value of 5000 MB is used.

## Additional Notes

- The application uses sockets for communication between different components.
- Multithreading is employed to handle concurrent tasks and communication with multiple clients and servers.
- The `JobFunction` class provides a sample job execution function for tasks.

# Contributors
- [@LuisFilipe6](https://github.com/LuisFilipe6)
- [@NopeGuy](https://github.com/NopeGuy)
- [@Bernazad](https://github.com/HBernaH)