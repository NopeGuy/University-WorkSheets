import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

class Bank {

    private static class Account {
        private int balance;
        private ReentrantLock l;
        Account(int balance) {
            this.balance = balance;
            this.l = new ReentrantLock();
        }
        int balance() { return balance; }
        boolean deposit(int value) {
            try {
                this.l.lock();
                balance += value;
            } finally {
                this.l.unlock();
            }
            return true;
        }

        boolean withdraw(int value) {
            try {
                this.l.lock();
                if (value > balance)
                    return false;
                balance -= value;
            } finally {
                this.l.unlock();
            }
            return true;
        }
    }

    private ReentrantLock lb = new ReentrantLock();
    private Map<Integer, Account> map = new HashMap<Integer, Account>();
    private int nextId = 0;

    // create account and return account id
    public int createAccount(int balance) {
        Account c = new Account(balance);
        int id=-1;
        try {
            lb.lock();
            id = nextId;
            nextId += 1;
        }finally {
            if(id != -1) {
                map.put(id, c); // Seria possivel colocar isto fora se fosse uma função thread safe
            }
            lb.unlock();
        }
        return id;
    }

    // close account and return balance, or 0 if no such account
    public int closeAccount(int id) {
        Account c;
        try{
            map.get(id).l.lock();
            c = map.remove(id);
        } finally {
            map.get(id).l.unlock(); // posso fazer isto já que o(a) objeto(conta) nao existe?????
        }
        if (c == null)
            return 0;
        return c.balance();
    }

    // account balance; 0 if no such account
    public int balance(int id) {
        Account c = map.get(id);
        if (c == null)
            return 0;
        return c.balance();
    }

    // deposit; fails if no such account
    public boolean deposit(int id, int value) {
        this.lb.lock();
        Account c = map.get(id);
        if (c == null){
            this.lb.unlock();
            return false;
        }
        c.l.lock();
        this.lb.unlock();
        boolean r = c.deposit(value);
        c.l.unlock();
        return r;
    }

    // withdraw; fails if no such account or insufficient balance
    public boolean withdraw(int id, int value) {
        Account c = map.get(id);
        if (c == null)
            return false;
        return c.withdraw(value);
    }

    // transfer value between accounts;
    // fails if either account does not exist or insufficient balance
    public boolean transfer(int from, int to, int value) {
        // USANDO o 2 phase lock
        Account cfrom, cto;
        this.lb.lock();
        cfrom = map.get(from);
        cto = map.get(to);

        if (cfrom == null || cto == null) {
            this.lb.unlock();
            return false;
        }

        try {
            cfrom.l.lock();
            cto.l.lock();
            this.lb.unlock();

            boolean r = cfrom.withdraw(value) && cto.deposit(value);
        }finally {
            cto.l.unlock();
            cfrom.l.unlock();
            return r;
        }

        /* // SEM USAR o 2 phase lock
        Account cMaior, cMenor;
        int maior = Math.max(from,to);
        int menor = Math.min(from,to);
        cMaior = map.get(maior);
        cMenor = map.get(to);
        try{
            cMaior.l.lock();
            cMenor.l.lock();
            map.get(from).withdraw(value);
            map.get(to).deposit(value);
        } finally {
            cMaior.l.unlock();
            cMenor.l.unlock();
        }
        if (cMaior == null || cMenor ==  null)
            return false;
        return true;
        */

    }

    // sum of balances in set of accounts; 0 if some does not exist
    public int totalBalance(int[] ids) {
        int total = 0;
        for (int i : ids) {
            Account c = map.get(i);
            if (c == null)
                return 0;
            total += c.balance();
        }
        return total;
  }

}