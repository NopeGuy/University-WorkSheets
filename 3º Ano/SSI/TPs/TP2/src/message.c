#include "../include/message.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <grp.h>
#include <pwd.h>
#include <dirent.h>
#include <unistd.h>
#include "../include/daemon.h"
#include <sys/types.h>
#include <sys/stat.h>
#include <time.h>
#include <fcntl.h>
#include <dirent.h>

void write_message(Message message, int server_fd_write)
{
  // Serialize the message
  char serialized_message[BUFFER_SIZE];
  sprintf(serialized_message, "%d %s %s %s %s %s", message.id, message.type, message.sender, message.group, message.receiver, message.content);

  // Write the serialized message to the server
  write(server_fd_write, serialized_message, strlen(serialized_message));
}

void *read_message(char *file_path)
{
  FILE *file = fopen(file_path, "r");
  char *buffer = malloc(BUFFER_SIZE);
  memset(buffer, 0, BUFFER_SIZE);

  if (file == NULL)
  {
    printf("Error opening file, check permissions.\n\n");
    return NULL;
  }

  fread(buffer, 1, BUFFER_SIZE, file);
  fclose(file);

  // separate message in buffer by $
  char *token = strtok(buffer, "$");
  printf("Sender: %s\n", token);
  token = strtok(NULL, "$");
  printf("Message: %s\n", token);
}

void handle_write_command(Message message, int server_fd_write, int counter)
{
  char file_path[512];
  sprintf(file_path, "%s/%s_%s_%d", MESSAGE_FOLDER, message.group, message.receiver, counter);

  printf("File path: %s\n", file_path);
  // set permissions for everyone to not be able to read and write
  chmod(file_path, 0000);
  // get receiver uid
  struct passwd *pwd = getpwnam(message.receiver);
  // get receiver gid
  struct group *grp = getgrnam(message.group);

  // check if the user exists
  if (pwd == NULL && grp == NULL)
  {
    char error_message[50];
    sprintf(error_message, "Does not exist\n");
    write(server_fd_write, error_message, strlen(error_message));
    return;
  }
  // if they are null set as no user and no group
  int uid = 0;
  int gid = 0;
  if (pwd == NULL)
  {
    gid = grp->gr_gid;
  }
  if (grp == NULL)
  {
    uid = pwd->pw_uid;
  }

  printf("Receiver uid: %d\n", uid);
  printf("Receiver gid: %d\n", gid);

  // Create the messages directory if it doesn't exist
  mkdir(MESSAGE_FOLDER, 0777);

  FILE *file = fopen(file_path, "w");
  if (file == NULL)
  {
    perror("Error opening file\n");
    return;
  }

  fprintf(file, "%s$%s\n", message.sender, message.content);

  fclose(file);

  // now give permissions to the user the is the receiver
  chown(file_path, uid, gid);
  // change permissions for owner
  if (pwd != NULL)
  {
    chmod(file_path, 0400);
  }
  // change permissions for group
  if (grp != NULL)
  {
    chmod(file_path, 0440);
  }
  char success_message[15];
  strcpy(success_message, "Message sent.\n");
  write(server_fd_write, success_message, strlen(success_message));
}

void handle_read_command(Message message, int server_fd_write)
{
  DIR *dir;
  struct dirent *entry;
  char response[BUFFER_SIZE];
  char file_path[512];
  int found = 0;

  // Open the directory
  dir = opendir(MESSAGE_FOLDER);
  if (dir == NULL)
  {
    perror("Error opening directory");
    write(server_fd_write, "Error reading messages\n", 23);
    return;
  }

  // Iterate over each entry in the directory
  while ((entry = readdir(dir)) != NULL)
  {
    // Check if the file name matches the pattern
    if (strstr(entry->d_name, "_") != NULL)
    {
      strcpy(response, entry->d_name);
      // Extract the ID from the file name
      char *token = strtok(entry->d_name, "_");
      for (int i = 0; i < 2; i++)
      {
        token = strtok(NULL, "_");
      }
      int id = atoi(token);

      // Check if the ID matches the specified ID
      if (id == message.id)
      {
        found = 1;
        // Construct full file path
        sprintf(file_path, "%s/%s", MESSAGE_FOLDER, entry->d_name);
        break;
      }
    }
  }

  // Close the directory
  closedir(dir);

  // If file with the specified ID is found, send its path
  if (found)
  {
    // preppend the "messages/" to the file path
    char full_file_path[512];
    strcpy(full_file_path, "messages/");
    strcat(full_file_path, response);
    write(server_fd_write, full_file_path, strlen(full_file_path));

    // Append the file name to the read_messages file
    FILE *read_messages_file = fopen("files/read_messages", "a");
    if (read_messages_file == NULL)
    {
      perror("Error opening read_messages file\n");
      return;
    }
    fprintf(read_messages_file, "%s\n", response);
    fclose(read_messages_file);
  }
  else
  {
    sprintf(response, "File with ID %d not found\n", message.id);
    write(server_fd_write, response, strlen(response));
  }
}

void handle_list_command(Message message, int server_fd_write)
{
  DIR *dir;
  struct dirent *entry;
  char response[BUFFER_SIZE];
  char file_path[512];
  char sender[50];
  char strid[10];
  char *ptr;

  dir = opendir(MESSAGE_FOLDER);
  if (dir == NULL)
  {
    perror("Error opening directory");
    write(server_fd_write, "Error listing messages\n", 23);
    return;
  }

  strcpy(response, "List of messages:\n");
  while ((entry = readdir(dir)) != NULL)
  {
    char filename[512];
    strcpy(filename, entry->d_name);
    // Check if the file name matches the pattern
    if (strstr(entry->d_name, message.sender) != NULL)
    {
      // Extract sender from the file (file has sender$message)
      sprintf(file_path, "%s/%s", MESSAGE_FOLDER, entry->d_name);
      FILE *file = fopen(file_path, "r");
      if (file == NULL)
      {
        perror("Error opening file");
        write(server_fd_write, "Error listing messages\n", 23);
        return;
      }
      fscanf(file, "%[^$]", sender);
      strtok_r(sender, "_", &ptr); // Remove receivergroup

      // Tokenize file name and set id to the value at position 2
      char *token = strtok(entry->d_name, "_");
      for (int i = 0; i < 2; i++)
      {
        token = strtok(NULL, "_");
      }
      int id = atoi(token);
      // check if entry is inside the read_messages file
      FILE *read_messages_file = fopen("files/read_messages", "r");
      if (read_messages_file == NULL)
      {
        perror("Error opening read_messages file\n");
        return;
      }
      char line[512];
      int found = 0;
      while (fgets(line, sizeof(line), read_messages_file))
      {
        printf("Filename: %s\n", filename);
        printf("Line: %s\n", line);
        if (strstr(line, filename) != NULL)
        {
          found = 1;
          break;
        }
      }
      // Append sender and ID to response string
      if(found != 1) sprintf(response + strlen(response), "Sender: %s, ID: %d\n", sender, id);
    }
  }
  closedir(dir);

  // Send the response to the client
  write(server_fd_write, response, strlen(response));
}

void handle_listall_command(Message message, int server_fd_write)
{
  DIR *dir;
  struct dirent *entry;
  char response[BUFFER_SIZE];
  char file_path[512];
  char sender[50];
  char strid[10];
  char *ptr;

  dir = opendir(MESSAGE_FOLDER);
  if (dir == NULL)
  {
    perror("Error opening directory");
    write(server_fd_write, "Error listing messages\n", 23);
    return;
  }

  strcpy(response, "List of messages:\n");
  while ((entry = readdir(dir)) != NULL)
  {
    // Check if the file name matches the pattern
    if (strstr(entry->d_name, message.sender) != NULL)
    {
      // Extract sender from the file (file has sender$message)
      sprintf(file_path, "%s/%s", MESSAGE_FOLDER, entry->d_name);
      FILE *file = fopen(file_path, "r");
      if (file == NULL)
      {
        perror("Error opening file");
        write(server_fd_write, "Error listing messages\n", 23);
        return;
      }
      fscanf(file, "%[^$]", sender);
      strtok_r(sender, "_", &ptr); // Remove receivergroup

      // Tokenize file name and set id to the value at position 2
      char *token = strtok(entry->d_name, "_");
      for (int i = 0; i < 2; i++)
      {
        token = strtok(NULL, "_");
      }
      int id = atoi(token);

      // get size of file
      fseek(file, 0, SEEK_END);
      int size = ftell(file);
      fseek(file, 0, SEEK_SET);

      // get date of file creation
      struct stat attr;
      char date[20];
      if (stat(file_path, &attr) == 0)
      {
        struct tm result;
        if (localtime_r(&attr.st_ctime, &result) != NULL)
        {
          strftime(date, 20, "%Y-%m-%d %H:%M:%S", &result);
          printf("File creation date: %s\n", date);
        }
        else
        {
          perror("Error getting local time");
        }
      }
      else
      {
        perror("Error getting file attributes");
      }

      // Append sender and ID to response string
      sprintf(response + strlen(response), "Sender: %s, ID: %d, Size: %d bytes, Date: %s\n", sender, id, size, date);
    }
  }
  closedir(dir);

  // Send the response to the client
  write(server_fd_write, response, strlen(response));
}

void handle_answer_command(Message message, int server_fd_write, int counter)
{
  // create new message for sender of the one you're answering with content
  // find file with specified id and get the sender
  DIR *dir;
  struct dirent *entry;
  char response[BUFFER_SIZE];
  char file_path[512];
  char sender[50];
  char strid[10];
  char *ptr;

  dir = opendir(MESSAGE_FOLDER);
  if (dir == NULL)
  {
    perror("Error opening directory");
    write(server_fd_write, "Error listing messages\n", 23);
    return;
  }

  while ((entry = readdir(dir)) != NULL)
  {
    // Check if the file name matches the pattern
    if (strstr(entry->d_name, message.sender) != NULL)
    {
      // Extract sender from the file (file has sender$message)
      sprintf(file_path, "%s/%s", MESSAGE_FOLDER, entry->d_name);
      FILE *file = fopen(file_path, "r");
      if (file == NULL)
      {
        perror("Error opening file");
        write(server_fd_write, "Error listing messages\n", 23);
        return;
      }
      fscanf(file, "%[^$]", sender);
      strtok_r(sender, "_", &ptr); // Remove receivergroup

      // Tokenize file name and set id to the value at position 2
      char *token = strtok(entry->d_name, "_");
      for (int i = 0; i < 2; i++)
      {
        token = strtok(NULL, "_");
      }
      int id = atoi(token);

      // Check if the ID matches the specified ID
      if (id == message.id)
      {
        // Construct full file path
        sprintf(file_path, "%s/%s", MESSAGE_FOLDER, entry->d_name);
        break;
      }
    }
  }

  closedir(dir);

  // create new message file
  char new_file_path[512];
  sprintf(new_file_path, "%s/%s_%s_%d", MESSAGE_FOLDER, "none", sender, counter);
  FILE *new_file = fopen(new_file_path, "w");
  if (new_file == NULL)
  {
    perror("Error opening file\n");
    return;
  }
  fprintf(new_file, "%s$%s\n", message.sender, message.content);
  fclose(new_file);
  // set permissions for everyone to not be able to read and write
  chmod(new_file_path, 0000);
  // get receiver uid
  struct passwd *pwd = getpwnam(sender);
  // get receiver gid
  struct group *grp = getgrnam(message.group);

  // check if the user exists
  if (pwd == NULL && grp == NULL)
  {
    char error_message[50];
    sprintf(error_message, "Does not exist\n");
    write(server_fd_write, error_message, strlen(error_message));
    return;
  }
  // if they are null set as no user and no group
  int uid = 0;
  int gid = 0;
  
  printf("Sender: %s\n", sender);
  uid = pwd->pw_uid;
  printf("Receiver uid: %d\n", uid);

  // now give permissions to the user the is the receiver
  chown(new_file_path, uid, gid);
  chmod(new_file_path, 0600);
  chmod(new_file_path, 0660);

  char success_message[15];
  strcpy(success_message, "Message sent.\n");
  write(server_fd_write, success_message, strlen(success_message));
}

void handle_delete_command(Message message, int server_fd_write)
{
  char file_path[512];
  sprintf(file_path, "%s/%s_%s_%d", MESSAGE_FOLDER, message.group, message.receiver, message.id);

  // Check if the file exists
  if (access(file_path, F_OK) == -1)
  {
    char error_message[50];
    sprintf(error_message, "File with ID %d not found or permissions insufficient.\n", message.id);
    write(server_fd_write, error_message, strlen(error_message));
    return;
  }

  // Delete the file
  remove(file_path);

  char success_message[15];
  strcpy(success_message, "Message deleted.\n");
  write(server_fd_write, success_message, strlen(success_message));
}