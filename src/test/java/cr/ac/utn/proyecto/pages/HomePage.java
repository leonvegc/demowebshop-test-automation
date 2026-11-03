  package cr.ac.utn.proyecto.pages;
import java.util.List;
 
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
 
import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.Esperas;

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

    // @CacheLookup: se guarda el boton para saber cuando la pagina se recargo (ver Esperas)
    @FindBy(css = ".search-box-button")
    @CacheLookup
    private WebElement botonBuscar;

    // Se usa el menu de categorias de la izquierda porque el de arriba se ve en MAYUSCULAS
    @FindBy(css = ".block-category-navigation")
    private WebElement menuCategorias;

    // Carrito del encabezado (el enlace tambien sale en el pie, por eso se limita a .header-links)
    @FindBy(css = ".header-links .ico-cart")
    @CacheLookup
    private WebElement enlaceCarrito;

    @FindBy(css = ".header-links .cart-qty")
    private WebElement contadorCarrito;

    // Barra de avisos (sale arriba al agregar un producto)
    @FindBy(id = "bar-notification")
    private WebElement barraAviso;

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
        Esperas.clicYEsperarRecarga(driver, botonBuscar);
        return new SearchPage(driver);
    }

    // Si se busca sin escribir nada, el sitio muestra una alerta.
    // Este metodo devuelve el texto de la alerta y la cierra.
    public String buscarSinTexto() {
        botonBuscar.click();
        Alert alerta = driver.switchTo().alert();
        String mensaje = alerta.getText();
        alerta.accept();
        return mensaje;
    }

    // Se usa By porque el nombre de la categoria cambia en cada prueba
    // y @FindBy no acepta parametros.
    public CategoryPage irACategoria(String nombre) {
        WebElement enlace = menuCategorias.findElement(By.linkText(nombre));
        Esperas.clicYEsperarRecarga(driver, enlace);
        return new CategoryPage(driver);
    }

    public CartPage irAlCarrito() {
        // Si acaba de salir la barra de avisos, tapa el enlace del carrito:
        // esperamos a que se esconda antes de hacer clic
        Esperas.esperarOculto(driver, barraAviso);
        Esperas.clicYEsperarRecarga(driver, enlaceCarrito);
        return new CartPage(driver);
    }

    // El contador se ve como "(2)": se quitan los parentesis para dejar solo el numero
    public int obtenerCantidadCarrito() {
        String texto = contadorCarrito.getText().replace("(", "").replace(")", "");
        return Integer.parseInt(texto);
    }

}
