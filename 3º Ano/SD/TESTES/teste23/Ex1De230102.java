package teste23;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

interface Cache {
    void put(int key, byte[] value) throws InterruptedException;
    byte[] get(int key);
    void evict(int key);
}

public class Ex1De230102 {
    public class CacheService implements Cache{
        
        ReentrantLock lock = new ReentrantLock();
        Map<Integer, CacheObj> cacheMap = new HashMap<>();
        int N = 10;
        ArrayList<CacheObj> queue = new ArrayList<>();

        @Override
        public void put(int key, byte[] value) throws InterruptedException {
            lock.lock();
            try{
                CacheObj cacheobject = new CacheObj(key, value);
                queue.add(cacheobject);
                while(cacheMap.size() >= N || queue.get(0) != cacheobject){
                    cacheobject.cond.await();    
                }
                queue.remove(0);
                cacheMap.put(key, cacheobject);
            }finally{
                lock.unlock();
            }
        }

        @Override
        public byte[] get(int key) {
            lock.lock();
            try{
                CacheObj c = cacheMap.get(key);
                return c.b.clone();
            }finally{
                lock.unlock();
            }
        }

        @Override
        public void evict(int key) {
            lock.lock();
            try{
                cacheMap.remove(key);
                signalWithPriority();
            }finally{
                lock.unlock();
            }       
        }

        public void signalWithPriority(){
            queue.get(0).cond.signal();
        }

        public class CacheObj {
            int id;
            byte[] b;
            Condition cond = lock.newCondition();

            public CacheObj(int id, byte[] b){
                this.id = id;
                this.b = b;
            }
        }
    }
}
