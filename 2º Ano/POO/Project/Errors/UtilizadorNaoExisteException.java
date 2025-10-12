package Errors;

public class UtilizadorNaoExisteException extends Exception {
    public UtilizadorNaoExisteException() {
        super("Utilizador não existe!");
    }
}