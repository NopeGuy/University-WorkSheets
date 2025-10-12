package proj;

public class LinhaIncorretaException extends Exception {
    public LinhaIncorretaException(){
        super();
    }

    public LinhaIncorretaException(String s){
        super(s);
    }
    
    public void printStackTrace() {
    }
}
