package g8;

import java.io.IOException;
import java.net.Socket;

public class TaggedConnection implements AutoCloseable {
    public static class Frame {
        public final int tag;
        public final byte[] data;

        public Frame(int tag, byte[] data) {
            this.tag = tag;
            this.data = data;
        }
    }

    public TaggedConnection(Socket socket) throws IOException {

    }

    public void send(Frame frame) throws IOException {

    }

    public void send(int tag, byte[] data) throws IOException {

    }

    public Frame receive() throws IOException {
        return null;

    }

    public void close() throws IOException {

    }
}