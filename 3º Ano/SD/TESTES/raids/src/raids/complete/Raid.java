package raids.complete;

import java.util.*;

public interface Raid {
    List<String> players();
    void waitStart() throws InterruptedException;
    void leave();
}

