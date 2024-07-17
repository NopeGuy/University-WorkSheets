package teste24;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Ex1De240104 {

    public class Managerio implements ManagerInterface {

        int R;
        int currentRaids = 0;
        ReentrantLock lock = new ReentrantLock();
        Condition cond = lock.newCondition();
        private RaidObj current = new RaidObj();
        private int maxMin = 0;
        private Queue<RaidObj> pending = new ArrayDeque<>();

        @Override
        public Raid join(String name, int minPlayers) throws InterruptedException {
            lock.lock();
            try {
                RaidObj raid = current;
                raid.players.add(name);
                maxMin = Math.max(maxMin, minPlayers);
                if (raid.players.size() == maxMin) {
                    raid.init();
                    tryStart(raid);
                    maxMin = 0;
                    current = new RaidObj();
                    cond.signalAll();
                } else {
                    while (current == raid)
                        cond.await();
                }
                return raid;
            } finally {
                lock.unlock();
            }
        }

        void tryStart(RaidObj raid) {
            if (currentRaids < R) {
                currentRaids += 1;
                raid.start();
            } else {
                pending.add(raid);
            }
        }
    }

    public class RaidObj extends Managerio implements Raid {

        List<String> players = new ArrayList<>();
        ReentrantLock lock = new ReentrantLock();
        Condition cond = lock.newCondition();
        boolean started = false;
        int playing;

        @Override
        public List<String> players() {
            lock.lock();
            try {
                return new ArrayList<>(players);
            } finally {
                lock.unlock();
            }
        }

        @Override
        public void waitStart() throws InterruptedException {
            lock.lock();
            try {
                while (!started) {
                    cond.await();
                }
            } finally {
                lock.unlock();
            }
        }

        void init(){
            players = new ArrayList<>(players);
            playing = players.size();
        }

        void start(){
            lock.lock();
            try{
                started = true;
                cond.signalAll();
            }
            finally{
                lock.unlock();
            }
        }

        @Override
        public void leave() {
            lock.lock();
            try {
                players.remove(0);
                cond.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public int getCurrentPlayers() {
            return this.players.size();
        }
    }

}

interface ManagerInterface {
    Raid join(String name, int minPlayers) throws InterruptedException;
}

interface Raid {
    List<String> players();

    void waitStart() throws InterruptedException;

    void leave();
}