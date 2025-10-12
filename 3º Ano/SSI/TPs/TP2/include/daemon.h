#ifndef DAEMON_H
#define DAEMON_H

#define SERVER_FIFO_READ "/tmp/server_fifo_read"
#define SERVER_FIFO_WRITE "/tmp/server_fifo_write"

int get_current_counter();

#endif /* DAEMON_H */