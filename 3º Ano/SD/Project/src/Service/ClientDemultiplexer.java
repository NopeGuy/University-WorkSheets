package Service;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ClientDemultiplexer {

    private final Connection c;
    private final ReentrantLock l = new ReentrantLock();
    private final Map<Integer, FrameClient> map = new HashMap<>();
    private IOException exception = null;

    private class FrameClient {
        int waiting = 0;
        Queue<Frame> queue = new ArrayDeque<>();
        Condition c = l.newCondition();

    }

    public ClientDemultiplexer(Connection conn) {
        this.c = conn;
    }

    public void start() {
        new Thread(() -> {
            try {
                while (true) {
                    Frame frame = c.receive();
                    l.lock();
                    try {
                        FrameClient frameClient = map.get(frame.tag);
                        if (frameClient == null) {
                            frameClient = new FrameClient();
                            map.put(frame.tag, frameClient);
                        }
                        frameClient.queue.add(frame);
                        frameClient.c.signal();
                    }
                    finally {
                        l.unlock();
                    }
                }
            }
            catch (IOException e) {
                exception = e;
            }
        }).start();
    }

    public void send(Frame frame) throws IOException {
        c.send(frame);
    }

    public void send(int tag, int taskid, byte[] data) throws IOException {
        c.sendData(tag, taskid, data);
    }

    public void sendString(int tag, int taskid, String d) throws IOException {
        c.sendData(tag, taskid, d.getBytes());
    }

    public Frame receive(int tag) throws IOException, InterruptedException {
        l.lock();
        FrameClient frameClient;
        try {
            frameClient = map.get(tag);
            if (frameClient == null) {
                frameClient = new FrameClient();
                map.put(tag, frameClient);
            }
            frameClient.waiting++;
            while(true) {
                if(!frameClient.queue.isEmpty()) {
                    frameClient.waiting--;
                    Frame reply = frameClient.queue.poll();
                    if (frameClient.waiting == 0 && frameClient.queue.isEmpty())
                        map.remove(tag);
                    return reply;
                }
                if (exception != null) {
                    throw exception;
                }
                frameClient.c.await();
            }
        }
        finally {
            l.unlock();
        }
    }


    public void close() throws IOException {
        c.close();
    }
}