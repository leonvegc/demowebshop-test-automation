package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
 
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
 
import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.DatosPrueba;
import cr.ac.utn.proyecto.pages.HomePage;
import cr.ac.utn.proyecto.pages.LoginPage;

public class LoginTest extends BaseTest {

     private static final String CLAVE = "123abc";
 
    /*
      Crea una cuenta nueva y cierra la sesion.
      Devuelve el correo de la cuenta creada.
      Asi cada prueba tiene su propia cuenta y no depende de otras pruebas.
     */
    private String crearCuentaNueva() {
        String correo = DatosPrueba.correoUnico();
 
        new HomePage(driver)
                .abrir()
                .irARegistro()
                .registrar("Test", "QA", correo, CLAVE, CLAVE)
                .continuar()
                .cerrarSesion();
 
        return correo;
    }
 
    @Test
    @DisplayName("CP-007 Login exitoso con credenciales validas")
    public void loginExitoso() {
        String correo = crearCuentaNueva();
 
        HomePage inicio = new HomePage(driver)
                .irALogin()
                .iniciarSesion(correo, CLAVE);
 
        assertTrue(inicio.haySesionIniciada());
        assertEquals(correo, inicio.obtenerCorreoDelEncabezado());
    }
 
    @Test
    @DisplayName("CP-008 Login con clave incorrecta")
    public void loginConClaveIncorrecta() {
        String correo = crearCuentaNueva();
 
        LoginPage login = new HomePage(driver)
                .irALogin()
                .iniciarSesionConError(correo, "123abd");
 
        assertTrue(login.obtenerMensajeError().contains("The credentials provided are incorrect"));
    }
 
    @Test
    @DisplayName("CP-009 Cierre de sesion")
    public void cierreDeSesion() {
        String correo = crearCuentaNueva();
 
        HomePage inicio = new HomePage(driver)
                .irALogin()
                .iniciarSesion(correo, CLAVE)
                .cerrarSesion();
 
        assertTrue(inicio.esVisitante());
    }


}
