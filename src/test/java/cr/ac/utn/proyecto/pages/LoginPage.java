package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

 private WebDriver driver;
 
    @FindBy(id = "Email")
    private WebElement campoCorreo;
 
    @FindBy(id = "Password")
    private WebElement campoClave;
 
    @FindBy(css = ".login-button")
    private WebElement botonLogin;
 
    // Cuadro rojo con el error cuando el login falla
    @FindBy(css = ".validation-summary-errors")
    private WebElement mensajeError;
 
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
 
   
    public HomePage iniciarSesion(String correo, String clave) {
        llenarYEnviar(correo, clave);
        return new HomePage(driver);
    }
 
    
    public LoginPage iniciarSesionConError(String correo, String clave) {
        llenarYEnviar(correo, clave);
        return this;
    }
 
    public String obtenerMensajeError() {
        return mensajeError.getText();
    }
 
    // Metodo de ayuda para no repetir el mismo codigo en los dos metodos de arriba
    private void llenarYEnviar(String correo, String clave) {
        campoCorreo.clear();
        campoCorreo.sendKeys(correo);
        campoClave.clear();
        campoClave.sendKeys(clave);
        botonLogin.click();
    }

}
