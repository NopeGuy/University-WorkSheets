package raids.complete;

import java.util.*;

public interface Manager {
    Raid join(String name, int minPlayers) throws InterruptedException;
}
