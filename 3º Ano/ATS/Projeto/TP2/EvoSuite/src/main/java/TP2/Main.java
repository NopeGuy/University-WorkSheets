package TP2;
public class Main {

    public static void main(String[] args) {
        Menu menu = new Menu();
        Vintage vintage = Vintage.load();
        Controlador controlador = new Controlador(menu, vintage);
        controlador.inicio_controlador();

    }

}