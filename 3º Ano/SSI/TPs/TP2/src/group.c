#include "../include/group.h"
#include <stdio.h>
#include <unistd.h>
#include <string.h>

char *get_group_owner(char *group_name)
{
  char file_path[512];
  sprintf(file_path, "files/groups", MESSAGE_FOLDER);
  FILE *file = fopen(file_path, "r");
  if (file == NULL)
  {
    perror("Error opening file");
    return NULL;
  }

  char *line = NULL;
  size_t len = 0;
  ssize_t read;
  char *owner = NULL;

  while ((read = getline(&line, &len, file)) != -1)
  {
    char *group = strtok(line, "$");
    char *user = strtok(NULL, "$");

    if (strcmp(group, group_name) == 0)
    {
      owner = user;
      break;
    }
  }

  fclose(file);
  return owner;
}

void write_groups_file(char *group_name, char *user)
{
  // write to file group$user and if the file already exists, append to it
  char file_path[512];
  // file path is "../files/groups"
  sprintf(file_path, "files/groups", MESSAGE_FOLDER);
  FILE *file = fopen(file_path, "a");
  if (file == NULL)
  {
    perror("Error opening file");
    return;
  }
  fprintf(file, "%s$%s\n", group_name, user);
  fclose(file);
}

void delete_group_groups_file(char *group_name)
{
  char file_path[512];
  sprintf(file_path, "files/groups", MESSAGE_FOLDER);
  FILE *file = fopen(file_path, "r");
  if (file == NULL)
  {
    perror("Error opening file");
    return;
  }

  char *line = NULL;
  size_t len = 0;
  ssize_t read;
  char *owner = NULL;

  FILE *temp = fopen("temp", "w");
  while ((read = getline(&line, &len, file)) != -1)
  {
    char *group = strtok(line, "$");
    char *user = strtok(NULL, "$");

    if (strcmp(group, group_name) != 0)
    {
      fprintf(temp, "%s$%s\n", group, user);
    }
  }

  fclose(file);
  fclose(temp);
  remove(file_path);
  rename("temp", file_path);
}

void handle_group_create(Message message, int server_fd_write)
{
  // Construct the command to create the group using system()
  char command[100];
  sprintf(command, "sudo groupadd %s", message.group);

  // Execute the command
  int status = system(command);
  if (status == 0)
  {
    write_groups_file(message.group, message.sender);
    char success_message[15];
    strcpy(success_message, "Group created.\n");
    write(server_fd_write, success_message, strlen(success_message));
  }
  else
  {
    char failure_message[15];
    strcpy(failure_message, "Unable to create group.\n");
    write(server_fd_write, failure_message, strlen(failure_message));
  }
}

void handle_group_add(Message message, int server_fd_write)
{
  char *owner = get_group_owner(message.group);
  if (owner == NULL)
  {
    char error_message[50];
    sprintf(error_message, "Group %s does not exist\n", message.group);
    write(server_fd_write, error_message, strlen(error_message));
    return;
  }
  owner[strcspn(owner, "\n")] = 0;
  owner[strcspn(owner, " ")] = 0;
  message.receiver[strcspn(message.receiver, "\n")] = 0;
  message.receiver[strcspn(message.receiver, " ")] = 0;

  if (strcmp(owner, message.sender) != 0)
  {
    char error_message[50];
    sprintf(error_message, "You are not the owner of the group %s\n", message.group);
    write(server_fd_write, error_message, strlen(error_message));
    return;
  }
  // execute command to add user to group
  char command[100];
  sprintf(command, "sudo usermod -a -G %s %s", message.group, message.receiver);
  int status = system(command);
  if (status == 0)
  {
    char success_message[15];
    strcpy(success_message, "User added.\n");
    write(server_fd_write, success_message, strlen(success_message));
  }
  else
  {
    char failure_message[15];
    strcpy(failure_message, "Unable to add user.\n");
    write(server_fd_write, failure_message, strlen(failure_message));
  }
}

void handle_group_remove(Message message, int server_fd_write)
{
  char *owner = get_group_owner(message.group);
  if (owner == NULL)
  {
    char error_message[50];
    sprintf(error_message, "Group %s does not exist\n", message.group);
    write(server_fd_write, error_message, strlen(error_message));
    return;
  }
  owner[strcspn(owner, "\n")] = 0;
  owner[strcspn(owner, " ")] = 0;
  message.sender[strcspn(message.sender, "\n")] = 0;
  message.sender[strcspn(message.sender, " ")] = 0;

  if (strcmp(owner, message.sender) != 0)
  {
    char error_message[50];
    sprintf(error_message, "You are not the owner of the group %s\n", message.group);
    write(server_fd_write, error_message, strlen(error_message));
    return;
  }
  // execute command to remove user from group
  char command[100];
  sprintf(command, "sudo gpasswd -d %s %s", message.receiver, message.group);
  int status = system(command);
  if (status != 0)
  {
    char failure_message[15];
    strcpy(failure_message, "Unable to remove user.\n");
    write(server_fd_write, failure_message, strlen(failure_message));
    return;
  }
  char success_message[15];
  strcpy(success_message, "User removed.\n");
  write(server_fd_write, success_message, strlen(success_message));
}

void handle_group_delete(Message message, int server_fd_write)
{
  char *owner = get_group_owner(message.group);
  if (owner == NULL)
  {
    char error_message[50];
    sprintf(error_message, "Group %s does not exist\n", message.group);
    write(server_fd_write, error_message, strlen(error_message));
    return;
  }
  owner[strcspn(owner, "\n")] = 0;
  owner[strcspn(owner, " ")] = 0;
  message.sender[strcspn(message.sender, "\n")] = 0;
  message.sender[strcspn(message.sender, " ")] = 0;
  if (strcmp(owner, message.sender) != 0)
  {
    char error_message[50];
    sprintf(error_message, "You are not the owner of the group %s\n", message.group);
    write(server_fd_write, error_message, strlen(error_message));
    return;
  }

  // Construct the command to create the group using system()
  char command[100];
  sprintf(command, "sudo groupdel %s", message.group);
  int status = system(command);
  if (status == 0)
  {
    delete_group_groups_file(message.group);
    char success_message[15];
    strcpy(success_message, "Group delete.\n");
    write(server_fd_write, success_message, strlen(success_message));
  }
  else
  {
    char failure_message[15];
    strcpy(failure_message, "Unable to delete group.\n");
    write(server_fd_write, failure_message, strlen(failure_message));
  }
}

void handle_group_list(Message message, int server_fd_write)
{
  // execute command to list users in group and save it to a file
  char command[100];
  sprintf(command, "sudo getent group %s | cut -d: -f4 > temp", message.group);
  int status = system(command);
  if (status != 0)
  {
    char failure_message[15];
    strcpy(failure_message, "Unable to list users.\n");
    write(server_fd_write, failure_message, strlen(failure_message));
    return;
  }
  // read the file and send the content to the client
  FILE *file = fopen("temp", "r");
  if (file == NULL)
  {
    perror("Error opening file");
    write(server_fd_write, "Error listing users\n", 20);
    return;
  }
  char response[BUFFER_SIZE];
  strcpy(response, "List of users:\n");
  char user[50];
  while (fscanf(file, "%s", user) != EOF)
  {
    sprintf(response + strlen(response), "%s\n", user);
  }
  fclose(file);
  remove("temp");
  write(server_fd_write, response, strlen(response));
}
