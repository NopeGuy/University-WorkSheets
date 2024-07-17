package raids.complete.opt;

import raids.complete.*;
import java.util.*;
import java.util.concurrent.locks.*;

public class ManagerImpl implements Manager {
    private Lock l = new ReentrantLock();
    private Condition cond = l.newCondition();

    public static final int R = 10;
    private int running = 0;
    private Queue<RaidImpl> pending = new ArrayDeque<>();

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
                tryStart(raid);
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

    void tryStart(RaidImpl raid) {
        if (running < R) {
            running += 1;
            raid.start();
        } else {
            pending.add(raid);
        }
    }

    void finished() {
        l.lock(); 
        running -= 1;
        RaidImpl raid = pending.poll();
        if (raid != null)
            tryStart(raid);
        l.unlock();
    }

    private class RaidImpl implements Raid {
        Lock rl = new ReentrantLock();
        Condition rcond = rl.newCondition();
    
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

        void start() {
            rl.lock();  
            started = true;
            rcond.signalAll();
            rl.unlock();
        }

        public void waitStart() throws InterruptedException {
            rl.lock();
            try {
                while (!started)
                    rcond.await();
            } finally {
                rl.unlock();
            }
        }

        public void leave() {
            rl.lock(); 
            playing -= 1;
            if (playing == 0)
                finished();
            rl.unlock();
        }
    }
}
