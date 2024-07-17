package recurso23;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Ex1De230123 implements Controlador{

    int N,V;
    int counter = 0;
    int vencedor;
    boolean started = false;
    boolean oneFinished = false;
    Map<Integer, Integer> kartistas;
    ReentrantLock lock = new ReentrantLock();
    Condition notReady = lock.newCondition();
    Condition stillRacing = lock.newCondition();

    public Ex1De230123(int N, int V){
        this.N = N;
        this.V = V;
        this.kartistas = new HashMap<>(N);
    }

    @Override
    public int reserva() {
        lock.lock();
        try{
            int karter = counter;
            kartistas.put(karter, 0);
            return karter;
        }
        finally{
            lock.unlock();
        }
    }

    @Override
    public void preparado(int kart) throws InterruptedException {
        lock.lock();
        try{
            while(kartistas.size() != N-1){
                notReady.await();
            }
            started = true;
            notReady.signalAll();
        }
        finally{
            lock.unlock();
        }
    }

    @Override
    public void completaVolta(int kart){
        lock.lock();
        try{
            if(kartistas.get(kart) == V-1){
                kartistas.remove(kart);
                if(oneFinished == false){
                    oneFinished = true;
                    vencedor = kart;
                }
                if(kartistas.size() == 0) stillRacing.signalAll();
            }else{
                if(kartistas.get(kart) < V){
                    kartistas.put(kart, kartistas.get(kart) + 1);
                }
            }
        }finally{
            lock.unlock();
        }
    }

    @Override
    public int[] voltasCompletas() {
        lock.lock();
        try{
            int[] voltas = new int[kartistas.size()];
            int i=0;
            for(int volta : kartistas.values()){
                voltas[i] = volta;
                i++;
            }
            return voltas;
        }
        finally{
            lock.unlock();
        }
    }

    @Override
    public int vencedor() throws InterruptedException {
        lock.lock();
        try{
            while(kartistas.size() != 0){
                stillRacing.await();
            }
        return vencedor;
        }
        finally{
            lock.unlock();
        }
    }

    public boolean getOneFinished(){
        return oneFinished;
    }

}




interface Controlador {
    int reserva();

    void preparado(int kart) throws InterruptedException;

    void completaVolta(int kart);

    int[] voltasCompletas();

    int vencedor() throws InterruptedException;
}
