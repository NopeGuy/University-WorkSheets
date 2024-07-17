package recurso24;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Ex1De240124 {

    private int usedBytesTotal = 0;
    private final int globalBytesLimit = 16000000;
    private final ReentrantLock lockManager = new ReentrantLock();
    private final Condition globalLimitCondition = lockManager.newCondition();

    public class Managerio implements Manager {
        private final Map<String, TransfObj> transfMap = new HashMap<>();
        private int counter = 0;

        @Override
        public String newTransfer() {
            lockManager.lock();
            try {
                TransfObj t = new TransfObj(Integer.toString(counter));
                counter++;
                transfMap.put(t.getId(), t);
                return t.getId();
            } finally {
                lockManager.unlock();
            }
        }

        @Override
        public Transfer getTransfer(String identifier) {
            lockManager.lock();
            try {
                TransfObj res = transfMap.get(identifier);
                if (res != null)
                    res.setCounter(res.getCounter() - 1);
                if (res.getCounter() == 0) {
                    transfMap.remove(identifier);
                }
                return res;
            } finally {
                lockManager.unlock();
            }
        }
    }

    public class TransfObj implements Transfer {
        private final int limit = 16000;
        private byte[] buffer = new byte[0];
        private final String id;
        private final ReentrantLock lockTransf = new ReentrantLock();
        private final Condition isFull = lockTransf.newCondition();
        private final Condition isEmpty = lockTransf.newCondition();
        private int counter = 2;

        TransfObj(String id) {
            this.id = id;
        }

        @Override
        public void enqueue(byte[] b) throws InterruptedException {
            lockTransf.lock();
            try {
                while (b.length + buffer.length > limit) {
                    isFull.await();
                }
                lockManager.lock();
                try {
                    while (usedBytesTotal + b.length > globalBytesLimit) {
                        globalLimitCondition.await();
                    }
                    usedBytesTotal += b.length;
                    buffer = Arrays.copyOf(buffer, buffer.length + b.length);
                    System.arraycopy(b, 0, buffer, buffer.length - b.length, b.length);
                } finally {
                    lockManager.unlock();
                }
                isEmpty.signalAll();
            } finally {
                lockTransf.unlock();
            }
        }

        @Override
        public byte[] dequeue() throws InterruptedException {
            lockTransf.lock();
            try {
                while (buffer.length == 0) {
                    isEmpty.await();
                }
                byte[] b = Arrays.copyOf(buffer, buffer.length);
                buffer = new byte[0];
                lockManager.lock();
                try {
                    usedBytesTotal -= b.length;
                    globalLimitCondition.signalAll();
                } finally {
                    lockManager.unlock();
                }
                isFull.signalAll();
                return b;
            } finally {
                lockTransf.unlock();
            }
        }

        public String getId() {
            return this.id;
        }

        public int getCounter() {
            return this.counter;
        }

        public void setCounter(int counter) {
            this.counter = counter;
        }
    }

    interface Manager {
        String newTransfer();

        Transfer getTransfer(String identifier);
    }

    interface Transfer {
        void enqueue(byte[] b) throws InterruptedException;

        byte[] dequeue() throws InterruptedException;
    }
}
