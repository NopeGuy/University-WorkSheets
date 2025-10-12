#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <dirent.h>
#include <pwd.h>
#include <grp.h>
#include <stdio.h>
#include <stdlib.h>
#include <dirent.h>
#include <string.h>
#include "../include/message.h"
#include "../include/daemon.h"

int counter = 1;

int get_current_counter()
{
    DIR *dir;
    struct dirent *entry;
    int current_max_id = 0;

    dir = opendir(MESSAGE_FOLDER);
    if (dir == NULL)
    {
        perror("Error opening directory");
        return -1; // Return -1 to indicate error
    }

    while ((entry = readdir(dir)) != NULL)
    {
        // Check if the file name matches the pattern
        if (strstr(entry->d_name, "_") != NULL)
        {
            char *token = strtok(entry->d_name, "_"); // Tokenize by underscore
            token = strtok(NULL, "_");                // Move to the second token (message receiver)
            token = strtok(NULL, "_");                // Move to the third token (message ID)
            int id = atoi(token);                     // Convert token to integer

            // Update current_max_id if necessary
            if (id > current_max_id)
            {
                current_max_id = id;
            }
        }
    }

    closedir(dir);
    return current_max_id;
}

int main()
{
    printf("Make sure daemon is ran as root\n");
    int server_fd_read, server_fd_write;
    char buffer[BUFFER_SIZE];
    counter = get_current_counter();
    memset(buffer, 0, BUFFER_SIZE);

    mkfifo(SERVER_FIFO_READ, 0666);
    mkfifo(SERVER_FIFO_WRITE, 0666);
    // set permissions for all users to be able to read and write
    chmod(SERVER_FIFO_READ, 0666);
    chmod(SERVER_FIFO_WRITE, 0666);

    server_fd_read = open(SERVER_FIFO_READ, O_RDONLY);
    server_fd_write = open(SERVER_FIFO_WRITE, O_WRONLY);
    Message *message = malloc(sizeof(Message));

    while (1)
    {
        if (read(server_fd_read, buffer, BUFFER_SIZE) > 0)
        {
            printf("Buffer: %s\n", buffer);
            printf("Message struct: %d %s %s %s %s %s\n", message->id, message->type, message->sender, message->group, message->receiver, message->content);
            sscanf(buffer, "%d %s %s %s %s %[^\n]", &message->id, message->type, message->sender, message->group, message->receiver, message->content);

            if (strlen(message->content) > 512)
            {
                printf("Content is too big\n");
                continue;
            }

            if (strcmp(message->type, "write") == 0)
            {
                printf("Content: %s\n", message->content);
                sscanf(buffer, "write %s %s %[^\n]", message->receiver, message->sender, message->content);

                handle_write_command(*message, server_fd_write, counter);
                counter++;
                memset(buffer, 0, BUFFER_SIZE);
            }
            else if (strcmp(message->type, "read") == 0)
            {
                handle_read_command(*message, server_fd_write);

                memset(buffer, 0, BUFFER_SIZE);
            }
            else if (strcmp(message->type, "list") == 0)
            {
                handle_list_command(*message, server_fd_write);

                memset(buffer, 0, BUFFER_SIZE);
            }
            else if (strcmp(message->type, "list_a") == 0)
            {
                handle_listall_command(*message, server_fd_write);

                memset(buffer, 0, BUFFER_SIZE);
            }
            else if (strcmp(message->type, "answer") == 0)
            {
                handle_answer_command(*message, server_fd_write, counter);
                counter++;

                memset(buffer, 0, BUFFER_SIZE);
            }
            else if (strcmp(message->type, "delete") == 0)
            {
                handle_delete_command(*message, server_fd_write);

                memset(buffer, 0, BUFFER_SIZE);
            }
            else if (strcmp(message->type, "group_create") == 0)
            {
                handle_group_create(*message, server_fd_write);

                memset(buffer, 0, BUFFER_SIZE);
            }
            else if (strcmp(message->type, "group_add") == 0)
            {
                handle_group_add(*message, server_fd_write);

                memset(buffer, 0, BUFFER_SIZE);
            }
            else if (strcmp(message->type, "group_remove") == 0)
            {
                handle_group_remove(*message, server_fd_write);

                memset(buffer, 0, BUFFER_SIZE);
            }
            else if (strcmp(message->type, "group_delete") == 0)
            {
                handle_group_delete(*message, server_fd_write);

                memset(buffer, 0, BUFFER_SIZE);
            }
            else if (strcmp(message->type, "group_list") == 0)
            {
                handle_group_list(*message, server_fd_write);

                memset(buffer, 0, BUFFER_SIZE);
            }
            else
            {
                printf("Invalid command\n");
                write(server_fd_write, "Invalid command\n", 16);
                memset(buffer, 0, BUFFER_SIZE);
            }
            fflush(stdout);
        }
    }

    free(message);
    close(server_fd_read);
    close(server_fd_write);
    unlink(SERVER_FIFO_READ);
    unlink(SERVER_FIFO_WRITE);

    return 0;
}