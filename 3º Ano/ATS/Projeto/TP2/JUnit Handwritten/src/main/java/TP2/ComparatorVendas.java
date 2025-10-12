package TP2;
import java.util.Comparator;

public class ComparatorVendas implements Comparator<Utilizador> {

    public int compare(Utilizador a1, Utilizador a2) {

        if (a1.getN_vendidos() == a2.getN_vendidos())
        return Long.compare(a1.getId(), a2.getId());

        if (a1.getN_vendidos() > a2.getN_vendidos())
            return -1;

        else
            return 1;
    }
}