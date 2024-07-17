package exameEE23;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Ex1De230715Scuffed implements HPC {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final int totalCores;
    private int freeCores;
    private int taskIdCounter = 0;
    private final Map<Integer, Integer> coreMap = new HashMap<>();
    private final Map<Integer, Long> coreTime = new HashMap<>();
    private final List<Task> waitingTasks = new ArrayList<>();

    public Ex1De230715Scuffed(int cores) {
        this.totalCores = cores;
        this.freeCores = cores;
    }

    public int inicio(int ncores) {
        lock.lock();
        try {
            taskIdCounter++;
            Task newTask = new Task(taskIdCounter, ncores);
            waitingTasks.add(newTask);
            
            while (true) {
                Task highestPriorityTask = getHighestPriorityTask();
                if (highestPriorityTask != null && highestPriorityTask.getId() == newTask.getId() && freeCores >= ncores) {
                    freeCores -= ncores;
                    coreMap.put(newTask.getId(), ncores);
                    waitingTasks.remove(newTask);
                    if(freeCores>0)condition.signal();
                    return newTask.getId();
                }
                if(freeCores>0)condition.signal();
                condition.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return ncores;
    }

    public void fim(int tarefa, long tempo) {
        lock.lock();
        try {
            int ncores = coreMap.remove(tarefa);
            freeCores += ncores;
            coreTime.put(tarefa, tempo);
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public Map<Integer, Long> historia() {
        lock.lock();
        try {
            return new HashMap<>(coreTime);
        } finally {
            lock.unlock();
        }
    }

    private Task getHighestPriorityTask() {
        Task highestPriorityTask = null;
        for (Task task : waitingTasks) {
            if (highestPriorityTask == null || task.getNcores() > highestPriorityTask.getNcores()) {
                highestPriorityTask = task;
            }
        }
        return highestPriorityTask;
    }

    private static class Task {
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

    interface HPC {
        int inicio(int ncores);

        void fim(int tarefa, long tempo);

        Map<Integer, Long> historia();
    }
}
