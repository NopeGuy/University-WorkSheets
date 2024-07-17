package raids.simple;

import java.util.*;

public interface Manager {
    List<String> join(String name, int minPlayers)
            throws InterruptedException;
}
