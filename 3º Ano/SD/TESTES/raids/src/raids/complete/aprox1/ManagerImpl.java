package raids.complete.aprox1;

import raids.complete.*;
import java.util.*;
import java.util.concurrent.locks.*;

public class ManagerImpl implements Manager {
    private Lock l = new ReentrantLock();
    private Condition cond = l.newCondition();

    private RaidImpl current = new RaidImpl();
    private int maxMin = 0;

    public Raid join(String name, int minPlayers) throws InterruptedException {
        l.lock();
        try {
            RaidImpl raid = current;
            raid.players.add(name);
            maxMin = Math.max(maxMin, minPlayers);
            if (raid.players.size() == maxMin) {
                raid.init();
                maxMin = 0;
                current = new RaidImpl();
                cond.signalAll();
            } else {
                while (current == raid)
                    cond.await();
            }
            return raid;
        } finally {
            l.unlock();
        }
    }

    private class RaidImpl implements Raid { 
        List<String> players = new ArrayList<>();
        boolean started = false;
        int playing; 
    
        void init() {
            players = Collections.unmodifiableList(players); 
            playing = players.size(); 
        }

        public List<String> players() {
            return players; 
        }

        public void waitStart() throws InterruptedException {
            /*  ... */ 
        }

        public void leave() {
            l.lock();
            playing -= 1; 
            l.unlock();
        }
    }
}
