package cl.global.logic.Excepcion;

public class PreconditionFailedExcepcion extends RuntimeException {

    private String mensaje;

    public PreconditionFailedExcepcion(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}
