package exameEE23;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Ex1De230715 implements HPC{
    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    int cores, freeCores;
    int count = 0;
    ArrayList<Task> waitingTasks = new ArrayList<>();
    HashMap<Integer, Integer> coreMap = new HashMap<>();
    HashMap<Integer, Long> coreTime = new HashMap<>();

    public Ex1De230715(int cores){
        this.cores= cores;
        this.freeCores = cores;
    }

    public int inicio(int ncores) throws InterruptedException{
        lock.lock();
        try{
            this.count++;
            Task newTask = new Task(count, ncores);
            waitingTasks.add(newTask);
            while(freeCores >= ncores){
                condition.await();
            }
            coreMap.put(newTask.getId(), newTask.getNcores());
            waitingTasks.remove(newTask);
            this.freeCores -= newTask.getNcores();
            if (freeCores > 0) getHighestPriorityTask();
            return newTask.getId();
        }
        finally{
            lock.unlock();
        }
    }

    public void fim(int tarefa, long tempo){
        lock.lock();
        try{
            freeCores += coreMap.get(tarefa);
            coreMap.remove(tarefa);
            coreTime.put(tarefa, tempo);
            getHighestPriorityTask();
            return;
        }
        finally{
            lock.unlock();
        }
    }

    public Map<Integer, Long> historia(){
        lock.lock();
        try{
            return new HashMap<>(coreTime);
        }
        finally{
            lock.unlock();
        }
    }

    private void getHighestPriorityTask() {
        Task highestPriorityTask = null;
        for (Task task : waitingTasks) {
            if (highestPriorityTask == null || task.getNcores() > highestPriorityTask.getNcores()) {
                highestPriorityTask = task;
            }
        }
        highestPriorityTask.taskCondition.signal();
    }

    private class Task {
        Condition taskCondition = lock.newCondition();
        private final int id;
        private final int ncores;

        Task(int id, int ncores) {
            this.id = id;
            this.ncores = ncores;
        }

        public int getId() {
            return id;
        }

        public int getNcores() {
            return ncores;
        }
    }
}