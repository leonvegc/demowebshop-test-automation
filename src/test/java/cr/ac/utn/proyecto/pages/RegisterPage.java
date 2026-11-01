package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage {
      private WebDriver driver;
 
    
    @FindBy(id = "gender-male")
    private WebElement opcionMasculino;
 
    @FindBy(id = "gender-female")
    private WebElement opcionFemenino;
 
    @FindBy(id = "FirstName")
    private WebElement campoNombre;
 
    @FindBy(id = "LastName")
    private WebElement campoApellido;
 
    @FindBy(id = "Email")
    private WebElement campoCorreo;
 
    @FindBy(id = "Password")
    private WebElement campoClave;
 
    @FindBy(id = "ConfirmPassword")
    private WebElement campoConfirmacion;
 
    @FindBy(id = "register-button")
    private WebElement botonRegister;
 
    // Mensajes de error que salen debajo de cada campo
    @FindBy(css = "span[data-valmsg-for='FirstName']")
    private WebElement errorNombre;
 
    @FindBy(css = "span[data-valmsg-for='LastName']")
    private WebElement errorApellido;
 
    @FindBy(css = "span[data-valmsg-for='Email']")
    private WebElement errorCorreo;
 
    @FindBy(css = "span[data-valmsg-for='Password']")
    private WebElement errorClave;
 
    @FindBy(css = "span[data-valmsg-for='ConfirmPassword']")
    private WebElement errorConfirmacion;
 
    
    @FindBy(css = ".validation-summary-errors")
    private WebElement errorGeneral;
 
   
    @FindBy(css = ".result")
    private WebElement mensajeResultado;
 
    @FindBy(css = ".register-continue-button")
    private WebElement botonContinuar;
 
    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
 
    public RegisterPage seleccionarMasculino() {
        opcionMasculino.click();
        return this;
    }
 
    public RegisterPage seleccionarFemenino() {
        opcionFemenino.click();
        return this;
    }
 
    /**
     * Llena el formulario y hace clic en Register.
     * Nos quedamos en la pagina de registro: ahi sale el mensaje de exito
     * o los mensajes de error.
     */
    public RegisterPage registrar(String nombre, String apellido, String correo,
                                  String clave, String confirmacion) {
        campoNombre.sendKeys(nombre);
        campoApellido.sendKeys(apellido);
        campoCorreo.sendKeys(correo);
        campoClave.sendKeys(clave);
        campoConfirmacion.sendKeys(confirmacion);
        botonRegister.click();
        return this;
    }
 
    
    public HomePage continuar() {
        botonContinuar.click();
        return new HomePage(driver);
    }
 
    public String obtenerMensajeResultado() {
        return mensajeResultado.getText();
    }
 
    public String obtenerErrorNombre() {
        return errorNombre.getText();
    }
 
    public String obtenerErrorApellido() {
        return errorApellido.getText();
    }
 
    public String obtenerErrorCorreo() {
        return errorCorreo.getText();
    }
 
    public String obtenerErrorClave() {
        return errorClave.getText();
    }
 
    public String obtenerErrorConfirmacion() {
        return errorConfirmacion.getText();
    }
 
    public String obtenerErrorGeneral() {
        return errorGeneral.getText();
    }

}
