package raids.simple.aprox1;

import raids.simple.Manager;
import java.util.*;
import java.util.concurrent.locks.*;

public class ManagerImpl implements Manager {
    private Lock l = new ReentrantLock();
    private Condition cond = l.newCondition();

    private int maxMin;
    private List<String> players = new ArrayList<>();

    public List<String> join(String name, int minPlayers)
            throws InterruptedException {
        l.lock();  
        try {
            players.add(name); 
            maxMin = Math.max(maxMin, minPlayers);
            if (players.size() == maxMin) {
                cond.signalAll(); 
            } else {
                while (players.size() < maxMin) 
                    cond.await();
            }   
            return players;
        } finally {
            l.unlock();  
        }
    }
}

