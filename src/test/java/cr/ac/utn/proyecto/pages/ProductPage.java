package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import cr.ac.utn.proyecto.base.Esperas;

public class ProductPage {
    private WebDriver driver;

    @FindBy(css = ".product-name h1")
    private WebElement titulo;

    @FindBy(css = ".add-to-cart-panel .qty-input")
    private WebElement campoCantidad;

    @FindBy(css = ".add-to-cart-panel .add-to-cart-button")
    private WebElement botonAgregar;

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
}
