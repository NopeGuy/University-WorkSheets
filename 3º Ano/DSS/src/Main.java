import business.Exceptions.MechanicAlreadyExistsException;
import business.Exceptions.MechanicOutOfHoursException;
import business.Exceptions.WorkstationEmptyException;
import business.controller.Controller;
import business.model.Model;
import ui.View;

public class Main {
    public static void main(String[] args)
            throws MechanicAlreadyExistsException, MechanicOutOfHoursException, WorkstationEmptyException {
        Model m = new Model();
        Controller c = new Controller(m);
        View menu = new View(c);
        menu.legitMenu();
    }
}
