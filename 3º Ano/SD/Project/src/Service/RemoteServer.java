package Service;

public class RemoteServer {

    public int serverId;

    public int serverMemory;

    public int availiableMemory;

    public Connection connection;

    public RemoteServer( int serverId, int memory, Connection con ) {
        this.serverId = serverId;
        this.serverMemory = memory;
        this.availiableMemory = memory;
        this.connection = con;
    }

    public boolean allocateMemory(int memory){
        if(this.availiableMemory - memory > 0){
            this.removeMemory(memory);
            return true;
        }

        return false;
    }

    public void removeMemory(int memory){
        this.availiableMemory -= memory;
    }

    public void addMemory(int memory) {
        this.availiableMemory += memory;
    }


}
