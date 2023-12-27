package g8;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FramedConnection implements AutoCloseable {
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Lock wLock;
    private Lock rLock;

    public FramedConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = new DataInputStream(socket.getInputStream());
        this.outputStream = new DataOutputStream(socket.getOutputStream());
        this.wLock = new ReentrantLock();
        this.rLock = new ReentrantLock();
    }

    public void send(byte[] data) throws IOException {
        wLock.lock();
        try {
            outputStream.writeInt(data.length);


            outputStream.write(data);
            outputStream.flush();
        } finally {
            wLock.unlock();
        }
    }

    public byte[] receive() throws IOException {
        rLock.lock();
        try {
            int length = inputStream.readInt();

            byte[] data = new byte[length];
            inputStream.readFully(data);
            return data;
        } finally {
            rLock.unlock();
        }
    }

    public void close() throws IOException {
        socket.close();
    }
}