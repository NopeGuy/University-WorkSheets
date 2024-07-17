package raids.simple.aprox2;

import raids.simple.Manager;
import java.util.*;
import java.util.concurrent.locks.*;

public class ManagerImpl implements Manager {
    private Lock l = new ReentrantLock();
    private Condition cond = l.newCondition();

    private RaidImpl current = new RaidImpl(); 

    public List<String> join(String name, int minPlayers)
            throws InterruptedException {
        l.lock();
        try {
            RaidImpl raid = current;
            raid.players.add(name);
            raid.maxMin = Math.max(raid.maxMin, minPlayers);
            if (raid.players.size() == raid.maxMin) {
                current = new RaidImpl(); 
                cond.signalAll();
            } else {
                while (raid.players.size() < raid.maxMin) 
                    cond.await();
            }
            return raid.players;
         } finally {
            l.unlock();
        }
    }

    private class RaidImpl { 
        List<String> players = new ArrayList<>();
        int maxMin = 0;
    }
}
