import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Barrier {
    private final int totalThreads;
    private int count = 0, epoch = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public Barrier(int n) {
        this.totalThreads = n;
    }

    public void await() throws InterruptedException {
        lock.lock();
        try {
            count++;
            if (count < totalThreads) {
                while (count < totalThreads) {
                    // Ainda não todas as threads chegaram, então aguarde.
                    condition.await();
                }
            }
            else{
                // Todas as threads chegaram, sinalize as outras threads para continuar.
                condition.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }


        public void await2() throws InterruptedException {
        lock.lock();
        try {
            int e = epoch;
            count++;
            if (count < totalThreads) {
                while (epoch == e) {
                    // Ainda não todas as threads chegaram, então aguarde.
                    condition.await();
                }
            }
            else{
                // Todas as threads chegaram, sinalize as outras threads para continuar.
                condition.signalAll();
                count = 0;
                epoch++;
            }
        } finally {
            lock.unlock();
        }
    }
}
