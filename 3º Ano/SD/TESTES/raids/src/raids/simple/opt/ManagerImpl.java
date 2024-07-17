package raids.simple.opt;

import raids.simple.Manager;
import java.util.*;
import java.util.concurrent.locks.*;

public class ManagerImpl implements Manager {
    private Lock l = new ReentrantLock();
    private Condition cond = l.newCondition();

    private int maxMin;
    private List<String> currentPlayers = new ArrayList<>();

    public List<String> join(String name, int minPlayers)
            throws InterruptedException {
        l.lock();
        try {
            List<String> players = currentPlayers;
            players.add(name);
            maxMin = Math.max(maxMin, minPlayers);
            if (players.size() == maxMin) {
                currentPlayers = new ArrayList<>();  
                maxMin = 0;
                cond.signalAll();
            } else {
                while (players == currentPlayers)  
                    cond.await();
            }
            return players;
        } finally {
            l.unlock();
        }
    }
}

