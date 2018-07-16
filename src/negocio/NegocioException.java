package negocio;

/**
 *
 * @author User
 */
public class NegocioException extends Exception {

    public NegocioException(String s) {
        super(s);
    }

    public NegocioException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NegocioException(Throwable throwable) {
        super(throwable);
    }
}
