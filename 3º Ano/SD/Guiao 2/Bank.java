import java.util.concurrent.locks.ReentrantLock;

public class Bank {

    private static class Account {
        ReentrantLock pastore = new ReentrantLock();
        private int balance;

        Account(int balance) {
            this.balance = balance;
        }

        int balance() {
            try {
                pastore.lock();
                return balance;
            } finally {
                pastore.unlock();
            }
        }

        boolean deposit(int value) {
            try {
                pastore.lock();
                balance += value;
                return true;
            } finally {
                pastore.unlock();
            }
        }

        boolean withdraw(int value) {
            try {
                pastore.lock();
                if (value > balance)
                    return false;
                balance -= value;
                return true;
            } finally {
                pastore.unlock();
            }
        }
    }

    // Bank slots and vector of accounts
    private int slots;
    private Account[] av;

    private ReentrantLock lockBanco = new ReentrantLock();

    public Bank(int n) {
        slots = n;
        av = new Account[slots];
        for (int i = 0; i < slots; i++)
            av[i] = new Account(0);
    }

    // Account balance
    public int balance(int id) {
        if (id < 0 || id >= slots)
            return 0;
        return av[id].balance();
    }

    // Deposit
    public boolean deposit(int id, int value) {
        if (id < 0 || id >= slots)
            return false;
        return av[id].deposit(value);
    }

    // Withdraw; fails if no such account or insufficient balance
    public boolean withdraw(int id, int value) {
        if (id < 0 || id >= slots)
            return false;
        return av[id].withdraw(value);
    }

    public boolean transfer(int from, int to, int value) {
        this.lockBanco.lock();
        if (to > from) {
            av[from].pastore.lock();
            av[to].pastore.lock();
        } else {
            av[from].pastore.lock();
            av[to].pastore.lock();
        }
        this.lockBanco.unlock();
        try {
            if (!this.withdraw(from, value))
                return false;
            this.deposit(to, value);
        } finally {
            av[from].pastore.unlock();
            av[to].pastore.unlock();
        }
        return true;
    }

    public int totalBalance() {
        int soma = 0;
        this.lockBanco.lock();
        try {
            for (Account a : av) {
                a.pastore.lock();
            }
            for (Account a : av) {
                soma += a.balance;
            }
        } finally {
            for (Account a : av) {
                a.pastore.unlock();
            }
        }
        this.lockBanco.unlock();
        return soma;
    }
}
