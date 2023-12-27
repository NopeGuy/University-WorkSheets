import java.util.*;
import java.util.concurrent.locks.*;

class WarehouseGreedy {
    private Map<String, Product> map = new HashMap<String, Product>();
    private Lock lock = new ReentrantLock();
    private Condition itemsUpdated = lock.newCondition();

    private class Product {
        int quantity = 0;
    }

    private Product get(String item) {
        Product p = map.get(item);
        if (p != null) return p;
        p = new Product();
        map.put(item, p);
        return p;
    }

    public void supply(String item, int quantity) {
        lock.lock();
        try {
            Product p = get(item);
            p.quantity += quantity;
            itemsUpdated.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void consume(Set<String> items) throws InterruptedException {
        lock.lock();
        try {
            while (!canFulfill(items)) {
                itemsUpdated.await();
            }
            for (String s : items) {
                get(s).quantity--;
            }
        } finally {
            lock.unlock();
        }
    }

    private boolean canFulfill(Set<String> items) {
        for (String s : items) {
            if (get(s).quantity <= 0) {
                return false;
            }
        }
        return true;
    }
}
