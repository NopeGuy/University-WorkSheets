package TP1;

import java.io.Serializable;

public class ViewLogin implements Serializable {
    private final Output out;

    public ViewLogin() {
        out = new Output();
    }

    public void printMenuLogin() {
        out.printMenus((new String[]{"Login", "Registar"}),"     MENU LOGIN",1);
    }

}
    

