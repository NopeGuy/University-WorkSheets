package teste23;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import teste23.Ex1De230102.CacheService;

public class Ex2De230102 {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(12345);
        CacheService cacheService = new CacheService();
        Map<Integer, Integer> uses = new HashMap<>();
        int M = 10;
        ReentrantLock lock = new ReentrantLock();

        while (true) {
            Socket client = server.accept();
            Worker worker = new Worker(client, cacheService, lock, uses, M);
            Thread t = new Thread(worker);
            t.start();
        }
    }

    public class Worker implements Runnable {
        Socket ronaldinho;
        Ex1De230102.CacheService cache;
        ReentrantLock lock;
        Map<Integer, Integer> uses;
        int M;
        public static int counter = 0;

        public Worker(Socket socket, CacheService cache, ReentrantLock lock, Map<Integer, Integer> uses, int M) {
            this.ronaldinho = socket;
            this.cache = cache;
            this.lock = lock;
            this.uses = uses;
            this.M = M;
        }

        @Override
        public void run() {
            try {
                DataInputStream is = new DataInputStream(new BufferedInputStream(ronaldinho.getInputStream()));
                DataOutputStream os = new DataOutputStream(new BufferedOutputStream(ronaldinho.getOutputStream()));
                String command;
                while ((command = is.readUTF()) != null) {
                    switch (command) {
                        case "put":
                            lock.lock();
                            try {
                                int key1 = counter;
                                int numberBytes = is.readInt();
                                byte[] value = new byte[numberBytes];
                                is.read(value);
                                cache.put(key1, value);
                                uses.put(key1, M + 1);
                                counter++;
                                timeToLive();
                                break;
                            } finally {
                                lock.unlock();
                            }
                        case "get":
                            lock.lock();
                            try {
                                int key2 = is.readInt();
                                byte[] data = cache.get(key2);
                                os.write(data);
                                os.flush();
                                uses.put(key2, M + 1);
                                timeToLive();
                                break;
                            } finally {
                                lock.unlock();
                            }
                        case "evict":
                            try {
                                int key3 = is.readInt();
                                cache.evict(key3);
                                uses.remove(key3);
                                timeToLive();
                                break;
                            } finally {
                                lock.unlock();
                            }
                        default:
                            os.writeUTF("Unknown command");
                            os.flush();
                            break;
                    }
                }
                is.close();
                os.close();
                ronaldinho.close();
            } catch (Exception e) {

            }
        }

        public void timeToLive() {
            for (Map.Entry<Integer, Integer> entry : uses.entrySet()) {
                if (entry.getValue() == 1) {
                    uses.remove(entry.getKey());
                    cache.evict(entry.getKey());
                }
                uses.put(entry.getKey(), entry.getValue() - 1);
            }
        }
    }
}
