import java.time.LocalDate;
import java.util.Arrays;

import static java.time.temporal.ChronoUnit.DAYS;

public class ex2 {
    private int capacity = 10;
    private int index = 0;
    public LocalDate[] datas = new LocalDate[capacity];


    public void insereData(LocalDate data) {
        if ( index >= capacity){
            capacity *=2;
            LocalDate[] newArr = new LocalDate[capacity];

            for (int i = 0; i <datas.length; i++){
                newArr[i] = datas[i];
            }

            datas = newArr;
        }
        datas[index++] = data;
    }

    public LocalDate dataMaisProxima(LocalDate data){
        LocalDate proxima = datas[0];

        for(int i = 1; i< index; i++){
            if(Math.abs(DAYS.between(proxima, data))> Math.abs(DAYS.between(datas[i], data))){
                proxima = datas[i];
            }
        }
        return proxima;
    }

    public String toString(){
        StringBuilder s = new StringBuilder("[");
        for (int i = 0; i<index;i++){
            if(i>0){
                s.append(", ");
            }

            s.append(datas[i]);
        }
        s.append("]");
        return s.toString();
    }
}
