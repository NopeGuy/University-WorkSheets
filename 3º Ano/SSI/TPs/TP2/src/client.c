#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <pwd.h>
#include "../include/message.h"
#include "../include/client.h"

#define SERVER_FIFO_READ "/tmp/server_fifo_read"
#define SERVER_FIFO_WRITE "/tmp/server_fifo_write"

void print_commands()
{
    printf("\nAvailable commands:\n\n");

    // Message commands
    printf("  Messages:\n");
    printf("    list:                               List non-read messages\n");
    printf("    list -a:                            List all messages\n");
    printf("    write group <group_name> <message>: Send message to a group\n");
    printf("    write user <username> <message>:    Send message to a user\n");
    printf("    read <id>:                          Read messages\n");
    printf("    answer <id> <message>:              Answer a message\n");
    printf("    delete <id>:                        Delete a message\n");

    // Group commands
    printf("  Group management:\n");
    printf("    group create <group_name>:          Create a group\n");
    printf("    group add <group_name> <client>:    Add user to group\n");
    printf("    group remove <group_name> <client>: Remove user from group\n");
    printf("    group delete <group_name>:          Delete a group\n");
    printf("    group list <group_name>:            List all users in a group\n");

    // Other commands
    printf("  Other:\n");
    printf("    exit:                               Exit the program\n");
}

int main()
{
    int server_fd_read, server_fd_write;
    char buffer[BUFFER_SIZE];
    struct passwd *pwd = getpwuid(getuid());
    Message message;

    // Print client's name
    printf("Client name: %s\n", pwd->pw_name);

    // Open server FIFO for writing and reading
    server_fd_write = open(SERVER_FIFO_READ, O_WRONLY);
    server_fd_read = open(SERVER_FIFO_WRITE, O_RDONLY);

    // Command line interface
    while (1)
    {
        // Print available commands
        print_commands();
        printf("\nEnter command: ");
        fgets(buffer, BUFFER_SIZE, stdin);

        if (strncmp(buffer, "write group", 11) == 0)
        {
            strcpy(message.type, "write");
            strcpy(message.sender, pwd->pw_name);
            strcpy(message.receiver, "none");
            sscanf(buffer, "write group %s %[^\n]", message.group, message.content);
        }
        else if (strncmp(buffer, "write user", 10) == 0)
        {
            strcpy(message.type, "write");
            strcpy(message.sender, pwd->pw_name);
            strcpy(message.group, "none");
            sscanf(buffer, "write user %s %[^\n]", message.receiver, message.content);
        }
        else if (strncmp(buffer, "read", 4) == 0)
        {
            strcpy(message.type, "read");
            strcpy(message.group, "root");
            strcpy(message.receiver, pwd->pw_name);
            strcpy(message.content, "read");
            // the id of the message is the number after the argument read
            message.id = atoi(buffer + 5);
            strcpy(message.sender, pwd->pw_name);
        }
        else if (strncmp(buffer, "answer", 6) == 0)
        {
            strcpy(message.type, "answer");
            strcpy(message.group, "none");
            strcpy(message.receiver, pwd->pw_name);
            sscanf(buffer, "answer %d %[^\n]", &message.id, message.content);
            strcpy(message.sender, pwd->pw_name);
        }
        else if (strncmp(buffer, "delete", 6) == 0)
        {
            strcpy(message.type, "delete");
            strcpy(message.group, "none");
            strcpy(message.receiver, pwd->pw_name);
            message.id = atoi(buffer + 7);
            strcpy(message.sender, pwd->pw_name);
        }
        else if (strncmp(buffer, "list -a", 7) == 0)
        {
            strcpy(message.type, "list_a");
            strcpy(message.group, "none");
            strcpy(message.receiver, pwd->pw_name);
            strcpy(message.content, "list");
            message.id = 0;
            strcpy(message.sender, pwd->pw_name);
        }
        else if (strncmp(buffer, "list", 4) == 0)
        {
            strcpy(message.type, "list");
            strcpy(message.group, "none");
            strcpy(message.receiver, pwd->pw_name);
            strcpy(message.content, "list");
            message.id = 0;
            strcpy(message.sender, pwd->pw_name);
        }
        else if (strncmp(buffer, "group create", 12) == 0)
        {
            strcpy(message.type, "group_create");
            strcpy(message.group, strtok(buffer + 12, " "));
            strcpy(message.receiver, pwd->pw_name);
            strcpy(message.content, "none");
            message.id = 0;
            strcpy(message.sender, pwd->pw_name);
        }
        else if (strncmp(buffer, "group add", 9) == 0)
        {
            strcpy(message.type, "group_add");
            strcpy(message.group, strtok(buffer + 9, " "));
            strcpy(message.receiver, strtok(NULL, " "));
            message.id = 0;
            strcpy(message.sender, pwd->pw_name);
        }
        else if (strncmp(buffer, "group remove", 12) == 0)
        {
            strcpy(message.type, "group_remove");
            strcpy(message.group, strtok(buffer + 12, " "));
            strcpy(message.receiver, strtok(NULL, " "));
            strcpy(message.content, "none");
            message.id = 0;
            strcpy(message.sender, pwd->pw_name);
        }
        else if (strncmp(buffer, "group delete", 12) == 0)
        {
            strcpy(message.type, "group_delete");
            strcpy(message.group, strtok(buffer + 12, " "));
            strcpy(message.receiver, pwd->pw_name);
            strcpy(message.content, "none");
            message.id = 0;
            strcpy(message.sender, pwd->pw_name);
        }
        else if (strncmp(buffer, "group list", 10) == 0)
        {
            strcpy(message.type, "group_list");
            strcpy(message.group, strtok(buffer + 10, " "));
            strcpy(message.receiver, pwd->pw_name);
            strcpy(message.content, "none");
            message.id = 0;
            strcpy(message.sender, pwd->pw_name);
        }
        else if (strncmp(buffer, "exit", 4) == 0)
        {
            return;
            break;
        }
        else
        {
            printf("Invalid command\n");
            continue;
        }

        // Write the message to the server
        write_message(message, server_fd_write);

        // Clear the buffer
        memset(buffer, 0, BUFFER_SIZE);

        // Read the response from the server
        read(server_fd_read, buffer, BUFFER_SIZE);
        if (strncmp(buffer, "messages/", 9) == 0)
        {
            printf("Buffer: %s\n", buffer);
            char *file_path = buffer;
            read_message(file_path);
        }
        else
        {
            printf("Daemon response:\n%s\n", buffer);
        }
    }

    // Close server FIFO
    close(server_fd_read);
    close(server_fd_write);

    return 0;
}