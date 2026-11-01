package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
 
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
 
import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.DatosPrueba;
import cr.ac.utn.proyecto.pages.HomePage;
import cr.ac.utn.proyecto.pages.RegisterPage;
 


public class RegistroTest extends BaseTest {

 private static final String CLAVE = "123abc";
 
    @Test
    @DisplayName("CP-001 Registro exitoso con datos válidos")
    public void registroExitoso() {
        String correo = DatosPrueba.correoUnico();
 
        RegisterPage registro = new HomePage(driver)
                .abrir()
                .irARegistro()
                .seleccionarMasculino()
                .registrar("Test", "QA", correo, CLAVE, CLAVE);
 
        assertTrue(registro.obtenerMensajeResultado().contains("Your registration completed"));
 
        // El encabezado es igual en todas las paginas, por eso usamos HomePage para leerlo
        HomePage inicio = new HomePage(driver);
        assertEquals(correo, inicio.obtenerCorreoDelEncabezado());
    }
 
    @Test
    @DisplayName("CP-003 Registro con correo de formato inválido")
    public void registroConCorreoInvalido() {
        RegisterPage registro = new HomePage(driver)
                .abrir()
                .irARegistro()
                .registrar("Test", "QA", "abc@", CLAVE, CLAVE);
 
        assertEquals("Wrong email", registro.obtenerErrorCorreo());
    }
 
    @Test
    @DisplayName("CP-004 Registro con contraseña de 5 caracteres")
    public void registroConClaveCorta() {
        String correo = DatosPrueba.correoUnico();
 
        RegisterPage registro = new HomePage(driver)
                .abrir()
                .irARegistro()
                .registrar("Test", "QA", correo, "12abc", "12abc");
 
        assertEquals("The password should have at least 6 characters.", registro.obtenerErrorClave());
    }
 
    @Test
    @DisplayName("CP-006 Registro con correo ya registrado")
    public void registroConCorreoRepetido() {
        String correo = DatosPrueba.correoUnico();
 
        // Paso 1: registrar una cuenta y cerrar la sesion
        new HomePage(driver)
                .abrir()
                .irARegistro()
                .registrar("Test", "QA", correo, CLAVE, CLAVE)
                .continuar()
                .cerrarSesion();
 
        // Paso 2: intentar registrar otra cuenta con el mismo correo
        RegisterPage registro = new HomePage(driver)
                .irARegistro()
                .registrar("Otro", "Usuario", correo, CLAVE, CLAVE);
 
        assertTrue(registro.obtenerErrorGeneral().contains("The specified email already exists"));
    }

}
