#ifndef MESSAGE_H
#define MESSAGE_H

#define BUFFER_SIZE 1024 // Define a buffer size for message serialization
#define MESSAGE_FOLDER "messages"

// Define a struct for a message
typedef struct
{
    int id;
    char type[50];
    char sender[100];
    char group[100];
    char receiver[100];
    char content[512];
} Message;

void write_message(Message message, int server_fd_write);
void *read_message(char *file_path);
void handle_write_command(Message message, int server_fd_write, int counter);
void handle_read_command(Message message, int server_fd_write);
void handle_list_command(Message message, int server_fd_write);
void handle_listall_command(Message message, int server_fd_write);
void handle_answer_command(Message message, int server_fd_write, int counter);
void handle_delete_command(Message message, int server_fd_write);

#endif /* MESSAGE_H */
