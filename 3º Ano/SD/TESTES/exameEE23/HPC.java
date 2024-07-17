package exameEE23;

import java.util.Map;

interface HPC {
    int inicio(int ncores) throws InterruptedException;

    void fim(int tarefa, long tempo);

    Map<Integer, Long> historia();
}