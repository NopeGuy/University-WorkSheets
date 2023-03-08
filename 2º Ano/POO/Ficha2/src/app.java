import java.util.Arrays;
import java.time.LocalDate;

public class app {
    public static void main(String[] args){
        //declarações
        ex1 e1 = new ex1();
        ex2 e2 = new ex2();

        //teste da pergunta 1
        int[] arr = {1,0,10,15,20};

        //a
        System.out.println("Minimo: " + e1.minimo(arr));
        //b
        System.out.println("SubArray: " + Arrays.toString(e1.entreIndices(arr, 1 ,4)));

        int[] b = {1, 1 ,15};
        //c
        System.out.println("Comuns: " + Arrays.toString(e1.comuns(arr, b)));

        //teste da pergunta 2

        //a
        for (int i = 0; i<3; i++){
            e2.insereData(LocalDate.of(2019,1,1));
        }
        System.out.println("Datas: " + Arrays.toString(e2.datas));

        //b

        e2.insereData(LocalDate.of(2018,1,1));
        e2.insereData(LocalDate.of(2019,1,1));
        e2.insereData(LocalDate.of(2020,1,1));

        System.out.println("Datas mais proxima de 2021-01-01: " + e2.dataMaisProxima(LocalDate.of(2021,1,1)));

        System.out.println(("Datas: " + e2.toString()));

    }
}
