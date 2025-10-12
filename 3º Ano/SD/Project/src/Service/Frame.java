package Service;

public class Frame {

    public final int tag;
    public final byte[] data;

    public final int taskid;

    public Frame(int tag, int taskid, byte[] data) {
        this.tag = tag;
        this.data = data;
        this.taskid = taskid;
    }
}