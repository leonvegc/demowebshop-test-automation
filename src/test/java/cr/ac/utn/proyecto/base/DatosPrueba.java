package cr.ac.utn.proyecto.base;

public class DatosPrueba {
     /*  Crea un correo distinto cada vez, usando la hora en milisegundos.
     */
    public static String correoUnico() {
        return "qa_" + System.currentTimeMillis() + "@test.com";
    }
}
