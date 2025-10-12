#ifndef GROUP_H
#define GROUP_H

#include "../include/message.h"

#define MESSAGE_FOLDER "messages"

char *get_group_owner(char *group_name);
void write_groups_file(char *group_name, char *user);
void delete_group_groups_file(char *group_name);
void handle_group_create(Message message, int server_fd_write);
void handle_group_add(Message message, int server_fd_write);
void handle_group_remove(Message message, int server_fd_write);
void handle_group_delete(Message message, int server_fd_write);
void handle_group_list(Message message, int server_fd_write);

#endif /* GROUP_H */
