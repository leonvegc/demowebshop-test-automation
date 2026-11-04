package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import cr.ac.utn.proyecto.base.Esperas;

public class ProductPage {
    private WebDriver driver;

    @FindBy(css = ".product-name h1")
    private WebElement titulo;

    @FindBy(css = ".add-to-cart-panel .qty-input")
    private WebElement campoCantidad;

    @FindBy(css = ".add-to-cart-panel .add-to-cart-button")
    private WebElement botonAgregar;

    @FindBy(css = ".add-to-compare-list-button")
    @CacheLookup
    private WebElement botonComparar;

    // Precio que se ve en la pagina del producto
    @FindBy(css = ".product-price span")
    private WebElement precio;

    // Opciones de "Build your own computer". Los numeros de los id son los del sitio.
    @FindBy(id = "product_attribute_16_5_4")
    private WebElement listaProcesador;

    @FindBy(id = "product_attribute_16_6_5")
    private WebElement listaRam;

    // Barra de avisos que aparece arriba (verde si se agrego, roja si hubo error)
    @FindBy(id = "bar-notification")
    private WebElement barraAviso;

    @FindBy(css = "#bar-notification .content")
    private WebElement textoAviso;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String obtenerTitulo() {
        return titulo.getText();
    }

    // Ejemplo de opcion: "2.2 GHz Intel Pentium Dual-Core E2200"
    public ProductPage elegirProcesador(String opcion) {
        new Select(listaProcesador).selectByVisibleText(opcion);
        return this;
    }

    // Ejemplo de opcion: "8GB [+60.00]"
    public ProductPage elegirRam(String opcion) {
        new Select(listaRam).selectByVisibleText(opcion);
        return this;
    }

    // El disco duro son botones de opcion (radio). Se hace clic en su etiqueta, por ejemplo "320 GB".
    // Se usa By porque el texto de la opcion cambia en cada prueba y @FindBy no acepta parametros.
    public ProductPage elegirDisco(String opcion) {
        driver.findElement(By.xpath("//label[contains(text(),'" + opcion + "')]")).click();
        return this;
    }

    public double obtenerPrecio() {
        return Double.parseDouble(precio.getText());
    }

    public ProductPage escribirCantidad(String cantidad) {
        campoCantidad.clear();
        campoCantidad.sendKeys(cantidad);
        return this;
    }

    /*
      El producto se agrega con ajax: la pagina no se recarga y sale una barra de aviso.
      Esperamos a que la barra se vea para poder leer el mensaje.
     */
    public ProductPage agregarAlCarrito() {
        botonAgregar.click();
        Esperas.esperarVisible(driver, barraAviso);
        return this;
    }

    public String obtenerMensajeAviso() {
        return textoAviso.getText();
    }

    public ComparePage agregarAComparar() {
        Esperas.clicYEsperarRecarga(driver, botonComparar);
        return new ComparePage(driver);
    }
}
