import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    private ReentrantLock lock = new ReentrantLock();

    private static class Account {
        private ReentrantLock lock = new ReentrantLock();
        private int balance;

        Account(int balance) {
            this.balance = balance;
        }

        int balance() {
            return balance;
        }

        boolean deposit(int value) {
            balance += value;
            return true;
        }

        boolean withdraw(int value) {
            if (value > balance)
                return false;
            balance -= value;
            return true;
        }
    }

    // Bank slots and vector of accounts
    private int slots;
    private Account[] av;


    public Bank(int n) {
        slots = n;
        av = new Account[slots];
        for (int i = 0; i < slots; i++) av[i] = new Account(0);
    }

    // Account balance
    public int balance(int id) {
        if (id < 0 || id >= slots)
            return 0;
        return av[id].balance();
    }

    // Deposit
    public boolean deposit(int id, int value) {
        av[id].lock.lock();
        if (id < 0 || id >= slots)
            return false;
        av[id].lock.unlock();
        return av[id].deposit(value);
    }

    // Withdraw; fails if no such account or insufficient balance
    public boolean withdraw(int id, int value) {
        av[id].lock.lock();
        if (id < 0 || id >= slots)
            return false;
        av[id].lock.unlock();
        return av[id].withdraw(value);
    }

    public void transfer(int from, int to, int i) {
        av[from].lock.lock();
        av[to].lock.lock();
        this.deposit(to, i);
        this.withdraw(from, i);
        av[from].lock.unlock();
        av[to].lock.unlock();
    }

    public int totalBalance() {
        int total = 0;

        try {
            for (Account account : av) {
                account.lock.lock();
            }
        } finally {
            for (Account account : av) {
                total += account.balance();
                account.lock.unlock();
            }
        }
        return total;
    }
}
