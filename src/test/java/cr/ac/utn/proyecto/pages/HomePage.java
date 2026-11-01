  package cr.ac.utn.proyecto.pages;
import java.util.List;
 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
 
import cr.ac.utn.proyecto.base.BaseTest;

public class HomePage {
    private WebDriver driver;
 
    // Enlaces del encabezado
    @FindBy(className = "ico-register")
    private WebElement enlaceRegister;
 
    @FindBy(className = "ico-login")
    private WebElement enlaceLogin;
 
    @FindBy(className = "ico-logout")
    private WebElement enlaceLogout;
 
    // Enlace con el correo del usuario (solo aparece con sesion iniciada)
    @FindBy(css = ".header-links .account")
    private WebElement enlaceCuenta;
 
    // Listas para saber si un enlace existe o no en la pagina.
    // Si la lista esta vacia, el enlace no esta.
    @FindBy(className = "ico-logout")
    private List<WebElement> listaLogout;
 
    @FindBy(className = "ico-login")
    private List<WebElement> listaLogin;
 
    // Buscador del encabezado
    @FindBy(id = "small-searchterms")
    private WebElement cajaBusqueda;

    @FindBy(css = ".search-box-button")
    private WebElement botonBuscar;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
 
    
    public HomePage abrir() {
        driver.get(BaseTest.URL_BASE);
        return this;
    }
 
    
    public RegisterPage irARegistro() {
        enlaceRegister.click();
        return new RegisterPage(driver);
    }
 
    
    public LoginPage irALogin() {
        enlaceLogin.click();
        return new LoginPage(driver);
    }
 
    
    public HomePage cerrarSesion() {
        enlaceLogout.click();
        return new HomePage(driver);
    }
 
    
    public String obtenerCorreoDelEncabezado() {
        return enlaceCuenta.getText();
    }
 
    
    public boolean haySesionIniciada() {
        return !listaLogout.isEmpty();
    }
 
    
    public boolean esVisitante() {
        return !listaLogin.isEmpty();
    }

    public SearchPage buscar(String texto) {
        cajaBusqueda.clear();
        cajaBusqueda.sendKeys(texto);
        botonBuscar.click();
        return new SearchPage(driver);
    }

}
